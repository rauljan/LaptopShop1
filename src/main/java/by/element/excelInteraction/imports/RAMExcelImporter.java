package by.element.excelInteraction.imports;

import by.element.model.RAM;
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
public class RAMExcelImporter {
    @NotNull
    public List<RAM> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var ramSheet = workbook.getSheetAt(0);

        var ramTableFields = new String[]{"Id", "Модель", "Цена"};
        if (TableValidator.isValidTableStructure(ramSheet, ramTableFields)) {
            var dataFormatter = new DataFormatter();
            var newRAMs = new ArrayList<RAM>();

            var modelColNum = 1;
            var memoryColNum = 2;
            var priceColNum = 3;

            for (Row row : ramSheet) {
                if (row.getRowNum() != 0) {
                    String ramModel = null;
                    int ramMemory = 0;
                    int ramPrice = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            ramModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum && NumberUtils.isParsable(cellValue))
                            ramMemory = Integer.parseInt(cellValue);
                        else if (cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            ramPrice = Integer.parseInt(cellValue);
                    }
                    addNewRAM(ramModel, ramMemory, ramPrice, newRAMs);
                }
            }
            workbook.close();
            return newRAMs;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewRAM(String ramModel, int ramMemory, int ramPrice, ArrayList<RAM> newRAMs) {
        if (InputValidator.stringContainsAlphabet(ramModel) && ramMemory >= 1) {
            var newRAM = new RAM(ramModel, ramMemory, ramPrice);
            newRAMs.add(newRAM);
        }
    }
}