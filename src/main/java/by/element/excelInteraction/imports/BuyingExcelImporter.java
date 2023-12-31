package by.element.excelInteraction.imports;

import by.element.model.Basket;
import by.element.model.Buying;
import by.element.model.Laptop;
import by.element.repos.BasketRepo;
import by.element.repos.LaptopRepo;
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
public class BuyingExcelImporter {
    private BasketRepo basketRepo;
    private LaptopRepo laptopRepo;

    public BuyingExcelImporter(BasketRepo basketRepo, LaptopRepo laptopRepo) {
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
    }

    @NotNull
    public List<Buying> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var buyingSheet = workbook.getSheetAt(0);

        var buyingTableFields = new String[]{"Id", "Общая цена", "Id корзины", "Время покупки",
                "Id ноутбука", "Модель ноутбука"};
        if (TableValidator.isValidTableStructure(buyingSheet, buyingTableFields)) {
            var dataFormatter = new DataFormatter();
            var newBuyings = new ArrayList<Buying>();

            var totalPriceColNum = 1;
            var basketIdColNum = 2;
            var laptopIdColNum = 4;

            for (Row row : buyingSheet) {
                if (row.getRowNum() != 0) {
                    int totalPrice = 0;
                    Basket basket = null;
                    Laptop laptop = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (NumberUtils.isParsable(cellValue)) {
                            if (cell.getColumnIndex() == totalPriceColNum)
                                totalPrice = Integer.parseInt(cellValue);
                            else if (cell.getColumnIndex() == basketIdColNum) {
                                var basketId = Integer.parseInt(cellValue);
                                if (basketRepo.findById(basketId).isPresent())
                                    basket = basketRepo.findById(basketId).get();
                            } else if (cell.getColumnIndex() == laptopIdColNum) {
                                var laptopId = Integer.parseInt(cellValue);
                                if (laptopRepo.findById(laptopId).isPresent())
                                    laptop = laptopRepo.findById(laptopId).get();
                            }
                        }
                    }
                    addNewBuying(totalPrice, basket, laptop, newBuyings);
                }
            }
            workbook.close();
            return newBuyings;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewBuying(int totalPrice, Basket basket, Laptop laptop,
                                           ArrayList<Buying> newBuyings) {
        if (totalPrice >= 5000 && laptop != null && basket != null) {
            var newBuying = new Buying(totalPrice, laptop, basket);
            newBuyings.add(newBuying);
        }
    }
}