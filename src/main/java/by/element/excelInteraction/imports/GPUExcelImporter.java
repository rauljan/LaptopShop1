package by.element.excelInteraction.imports;

import by.element.model.GPU;
import by.element.inputService.InputValidator;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class GPUExcelImporter {
    @NotNull
    public List<GPU> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var gpuSheet = workbook.getSheetAt(0);

        var gpuTableFields = new String[]{"Id", "Модель", "Объем памяти", "Цена"};
        if (TableValidator.isValidTableStructure(gpuSheet, gpuTableFields)) {
            var dataFormatter = new DataFormatter();
            var newGPUs = new ArrayList<GPU>();

            var modelColNum = 1;
            var memoryColNum = 2;
            var priceColNum = 3;

            for (Row row : gpuSheet) {
                if (row.getRowNum() != 0) {
                    String gpuModel = null;
                    int gpuMemory = 0;
                    int gpuPrice = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            gpuModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum && NumberUtils.isParsable(cellValue))
                            gpuMemory = Integer.parseInt(cellValue);
                        else if (cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            gpuPrice = Integer.parseInt(cellValue);

                    }
                    addNewGPU(gpuModel, gpuMemory, gpuPrice, newGPUs);
                }
            }
            workbook.close();
            return newGPUs;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewGPU(String gpuModel, int gpuMemory, int price, ArrayList<GPU> newGPUs) {
        if (InputValidator.stringContainsAlphabet(gpuModel) && gpuMemory >= 1 && price >= 1) {
            var newGPU = new GPU(gpuModel, gpuMemory, price);
            newGPUs.add(newGPU);
        }
    }
}