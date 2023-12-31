package by.element.excelInteraction.exports;

import by.element.model.Display;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("displayExcelView")
@Lazy
public class DisplayExcelExporter extends ExcelExporter {
    public DisplayExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Display> displays = (List<Display>) model.get("displays");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Displays sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, displays, styler);

        response.setHeader("Content-Disposition", "attachment; filename=displays-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Модель");
        header.createCell(2).setCellValue("Тип");
        header.createCell(3).setCellValue("Диагональ");
        header.createCell(4).setCellValue("Разрешение");
        styler.setHeaderRowStyle(header, excelSheet);
    }


    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Display> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getModel());
            generalRow.createCell(2).setCellValue(row.getType());
            generalRow.createCell(3).setCellValue(row.getDiagonal());
            generalRow.createCell(4).setCellValue(row.getResolution());
            styler.setGeneralRowStyle(generalRow);
        }
    }
}