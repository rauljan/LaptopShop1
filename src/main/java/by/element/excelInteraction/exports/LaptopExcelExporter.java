package by.element.excelInteraction.exports;

import by.element.model.Laptop;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("laptopExcelView")
@Lazy
public class LaptopExcelExporter extends ExcelExporter {
    public LaptopExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Laptop> laptops = (List<Laptop>) model.get("laptops");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Laptops sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, laptops, styler);

        response.setHeader("Content-Disposition", "attachment; filename=laptops-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Бренд");
        header.createCell(2).setCellValue("Модель");
        header.createCell(3).setCellValue("Тип");
        header.createCell(4).setCellValue("Сборка");
        styler.setHeaderRowStyle(header, excelSheet);
    }


    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Laptop> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getLabel().getBrand());
            generalRow.createCell(2).setCellValue(row.getLabel().getModel());
            generalRow.createCell(3).setCellValue(row.getType() != null
                    ? row.getType().getName()
                    : "Отсутствует");
            generalRow.createCell(4).setCellValue(row.getHardware() != null
                    ? row.getHardware().getAssemblyName()
                    : "Отсутствует");
            styler.setGeneralRowStyle(generalRow);
        }
    }
}