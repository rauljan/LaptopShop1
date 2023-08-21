package by.element.excelInteraction.exports;

import by.element.model.Employee;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("employeeExcelView")
@Lazy
public class EmployeeExcelExporter extends ExcelExporter {
    public EmployeeExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Employee> employees = (List<Employee>) model.get("employees");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Employees sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, employees, styler);

        response.setHeader("Content-Disposition", "attachment; filename=employees-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Фамилия");
        header.createCell(3).setCellValue("№ магазина");
        header.createCell(4).setCellValue("Адрес магазина");
        header.createCell(5).setCellValue("Статус");
        styler.setHeaderRowStyle(header, excelSheet);
    }


    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Employee> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getFirstName());
            generalRow.createCell(2).setCellValue(row.getSecondName());
            if (row.getShop() == null) {
                generalRow.createCell(3).setCellValue("Закрыто");
                generalRow.createCell(4).setCellValue("Закрыто");
            } else {
                generalRow.createCell(3).setCellValue(row.getShop().getId());
                generalRow.createCell(4).setCellValue(row.getShop().getAddress());
            }
            generalRow.createCell(5).setCellValue(row.getIsWorking() ? "Работает" : "Уволен");
            styler.setGeneralRowStyle(generalRow);
        }
    }
}