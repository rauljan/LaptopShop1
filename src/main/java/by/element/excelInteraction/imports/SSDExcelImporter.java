package by.element.excelInteraction.imports;

import by.element.inputService.InputValidator;
import by.element.model.SSD;
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
public class SSDExcelImporter  {
    @NotNull

    public List<SSD> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var ssdSheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Модель", "Объем памяти"};
        if (TableValidator.isValidTableStructure(ssdSheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newSSDs = new ArrayList<SSD>();

            String ssdModel = null;
            var modelColNum = 1;
            int ssdMemory = 0;
            var memoryColNum = 2;
            int ssdPrice = 0;
            var priceColNum = 3;

            for (Row row : ssdSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            ssdModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum && NumberUtils.isParsable(cellValue))
                            ssdMemory = Integer.parseInt(cellValue);
                        else if (cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            ssdPrice = Integer.parseInt(cellValue);
                    }
                if (InputValidator.stringContainsAlphabet(ssdModel) && ssdMemory >= 1) {
                    var newSSD = new SSD(ssdModel, ssdMemory, ssdPrice);
                    newSSDs.add(newSSD);

                }
            }
            workbook.close();
            return newSSDs;
        } else
            throw new IllegalArgumentException();
    }
}