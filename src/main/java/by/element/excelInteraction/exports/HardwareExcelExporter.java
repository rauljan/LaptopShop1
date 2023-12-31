package by.element.excelInteraction.exports;

import by.element.model.Hardware;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service("hardwareExcelView")
@Lazy
public class HardwareExcelExporter extends ExcelExporter {
    public HardwareExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        super(rowsStylerBuilder);
    }

    @Override
    protected void buildExcelDocument(@NotNull Map<String, Object> model, @NotNull Workbook workbook,
                                      @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        @SuppressWarnings("unchecked") List<Hardware> hardware = (List<Hardware>) model.get("hardware");
        var currentDateTime = timeProvider.getCurrentDateTime();
        var sheet = workbook.createSheet("Hardware sheet");
        sheet.setFitToPage(true);

        var styler = rowsStylerBuilder.getRowStyler(workbook);
        setExcelHeader(sheet, styler);
        setExcelRows(sheet, hardware, styler);

        response.setHeader("Content-Disposition", "attachment; filename=hardware-sheet " + currentDateTime + ".xlsx");
    }


    public void setExcelHeader(@NotNull Sheet excelSheet, @NotNull RowsStyler styler) {
        var header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Название сборки");
        header.createCell(2).setCellValue("Модель процесора");
        header.createCell(3).setCellValue("Модель видеокарты");
        header.createCell(4).setCellValue("Модель дисплея");
        header.createCell(5).setCellValue("Модель оперативной памяти");
        header.createCell(6).setCellValue("Модель SSD диска");
        header.createCell(7).setCellValue("Модель HDD диска");
        styler.setHeaderRowStyle(header, excelSheet);
    }

    public void setExcelRows(@NotNull Sheet excelSheet, @NotNull List<Hardware> rows, RowsStyler styler) {
        var rowCount = 1;
        for (var row : rows) {
            var generalRow = excelSheet.createRow(rowCount++);
            generalRow.createCell(0).setCellValue(row.getId());
            generalRow.createCell(1).setCellValue(row.getAssemblyName());
            generalRow.createCell(2).setCellValue(row.getCpu() != null
                    ? row.getCpu().getModel()
                    : "Відсутній");
            generalRow.createCell(3).setCellValue(row.getGpu() != null
                    ? row.getGpu().getModel()
                    : "Відсутній");
            generalRow.createCell(4).setCellValue(row.getDisplay() != null
                    ? row.getDisplay().getModel()
                    : "Відсутній");
            generalRow.createCell(5).setCellValue(row.getRam() != null
                    ? row.getRam().getModel()
                    : "Відсутній");
            generalRow.createCell(6).setCellValue(row.getSsd() != null
                    ? row.getSsd().getModel()
                    : "Відсутній");
            generalRow.createCell(7).setCellValue(row.getHdd() != null
                    ? row.getHdd().getModel()
                    : "Відсутній");
            styler.setGeneralRowStyle(generalRow);
        }
    }
}