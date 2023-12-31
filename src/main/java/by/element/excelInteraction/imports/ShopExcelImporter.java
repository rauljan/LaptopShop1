package by.element.excelInteraction.imports;

import by.element.model.Shop;
import by.element.inputService.InputValidator;
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
public class ShopExcelImporter {
    @NotNull
    public List<Shop> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var shopSheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Адрес"};
        if (TableValidator.isValidTableStructure(shopSheet, typeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newShops = new ArrayList<Shop>();

            var addressColNum = 1;

            for (Row row : shopSheet) {
                if (row.getRowNum() != 0) {
                    String address = null;

                    for (Cell cell : row)
                        if (cell.getColumnIndex() == addressColNum)
                            address = dataFormatter.formatCellValue(cell);
                    addNewShop(address, newShops);
                }
            }
            workbook.close();
            return newShops;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewShop(String address, ArrayList<Shop> newShops) {
        if (InputValidator.stringContainsAlphabet(address)) {
            var newShop = new Shop(address);
            newShops.add(newShop);
        }
    }
}