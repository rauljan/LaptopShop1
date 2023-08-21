package by.element.excelInteraction.exports;

import by.element.model.Basket;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("basketExcelView")
@Lazy
public class BasketExcelExporter extends ExcelExporter {
    public BasketExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Basket> baskets = (List<Basket>) model.get("baskets");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Baskets sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, baskets, styler);

        response.setHeader("Content-Disposition", "attachment; filename=baskets-sheet " + currentDateTime + ".xlsx");
    }

    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Id продавца");
        header.createCell(2).setCellValue("Имя продавца");
        header.createCell(3).setCellValue("Фамилия продавца");
        header.createCell(4).setCellValue("Адрес магазина");
        header.createCell(5).setCellValue("Id покупателя");
        header.createCell(6).setCellValue("Имя покупателя");
        header.createCell(7).setCellValue("Фамилия покупателя");
        header.createCell(8).setCellValue("Время покупки");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Basket> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            if (row.getEmployee() == null) {
                generalRow.createCell(1).setCellValue("Удалено");
                generalRow.createCell(2).setCellValue("Удалено");
                generalRow.createCell(3).setCellValue("Удалено");
                generalRow.createCell(4).setCellValue("Удалено");
            } else {
                generalRow.createCell(1).setCellValue(row.getEmployee().getId());
                generalRow.createCell(2).setCellValue(row.getEmployee().getFirstName());
                generalRow.createCell(3).setCellValue(row.getEmployee().getSecondName());
                generalRow.createCell(4).setCellValue(row.getEmployee().getShop().getAddress());
            }
            if (row.getClient() == null) {
                generalRow.createCell(5).setCellValue("Удалено");
                generalRow.createCell(6).setCellValue("Удалено");
                generalRow.createCell(7).setCellValue("Удалено");
            } else {
                generalRow.createCell(5).setCellValue(row.getClient().getId());
                generalRow.createCell(6).setCellValue(row.getClient().getFirstName());
                generalRow.createCell(7).setCellValue(row.getClient().getSecondName());
            }
            generalRow.createCell(8).setCellValue(row.getDateTime().toString());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}