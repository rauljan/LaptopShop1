package by.element.excelInteraction.imports;

import by.element.model.HDD;
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
public class HDDExcelImporter {
    @NotNull
    public List<HDD> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var hddSheet = workbook.getSheetAt(0);

        var hddTableFields = new String[]{"Id", "Модель", "Объем памяти"};
        if (TableValidator.isValidTableStructure(hddSheet, hddTableFields)) {
            var dataFormatter = new DataFormatter();
            var newHDDs = new ArrayList<HDD>();

            var modelColNum = 1;
            var memoryColNum = 2;
            var priceColNum = 3;

            for (Row row : hddSheet) {
                if (row.getRowNum() != 0) {
                    String hddModel = null;
                    int hddMemory = 0;
                    int hddPrice = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            hddModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum && NumberUtils.isParsable(cellValue))
                            hddMemory = Integer.parseInt(cellValue);
                        else if (cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            hddPrice = Integer.parseInt(cellValue);
                    }
                    addNewHDD(hddModel, hddMemory, hddPrice, newHDDs);
                }
            }
            workbook.close();
            return newHDDs;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewHDD(String hddModel, int hddMemory, int price, ArrayList<HDD> newHDDs) {
        if (InputValidator.stringContainsAlphabet(hddModel) && hddMemory >= 1) {
            var newHDD = new HDD(hddModel, hddMemory, price);
            newHDDs.add(newHDD);
        }
    }
}
