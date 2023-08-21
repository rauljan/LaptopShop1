package by.element.excelInteraction.imports;

import by.element.model.Display;
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
public class DisplayExcelImporter {
    @NotNull
    public List<Display> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var displaySheet = workbook.getSheetAt(0);

        var displayTableFields = new String[]{"Id", "Модель", "Тип", "Диагональ", "Разрешение"};
        if (TableValidator.isValidTableStructure(displaySheet, displayTableFields)) {
            var dataFormatter = new DataFormatter();
            var newDisplays = new ArrayList<Display>();

            var modelColNum = 1;
            var typeColNum = 2;
            var diagonalColNum = 3;
            var resolutionColNum = 4;
            var priceColNum = 5;

            for (Row row : displaySheet) {
                if (row.getRowNum() != 0) {
                    String model = null;
                    String type = null;
                    String diagonal = null;
                    String resolution = null;
                    int price = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            model = cellValue;
                        else if (cell.getColumnIndex() == typeColNum)
                            type = cellValue;
                        else if (cell.getColumnIndex() == diagonalColNum && NumberUtils.isParsable(cellValue))
                            diagonal = cellValue;
                        else if (cell.getColumnIndex() == resolutionColNum)
                            resolution = cellValue;
                        else if(cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            price = Integer.parseInt(cellValue);
                    }
                    addNewDisplay(model, type, diagonal, resolution, price, newDisplays);
                }
            }
            workbook.close();
            return newDisplays;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewDisplay(String model, String type, String diagonal, String resolution, Integer price,
                                      ArrayList<Display> newDisplays) {
        if (InputValidator.stringContainsAlphabet(model) && InputValidator.stringContainsAlphabet(type) && InputValidator.stringContainsAlphabet(resolution)) {
            var newDisplay = new Display(model, type, diagonal, resolution, price);
            newDisplays.add(newDisplay);
        }
    }
}