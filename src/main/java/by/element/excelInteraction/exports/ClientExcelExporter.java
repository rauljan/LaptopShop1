package by.element.excelInteraction.exports;

import by.element.model.Client;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("clientExcelView")
@Lazy
public class ClientExcelExporter extends ExcelExporter {
    public ClientExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Client> clients = (List<Client>) model.get("clients");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Clients sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, clients, styler);

        response.setHeader("Content-Disposition", "attachment; filename=clients-sheet " + currentDateTime + ".xlsx");
    }

    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Фамилия");
        header.createCell(3).setCellValue("Дата регистрации");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Client> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getFirstName());
            generalRow.createCell(2).setCellValue(row.getSecondName());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}