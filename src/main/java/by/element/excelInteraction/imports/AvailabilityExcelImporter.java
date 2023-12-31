package by.element.excelInteraction.imports;

import by.element.model.Availability;
import by.element.model.Laptop;
import by.element.model.Shop;
import by.element.repos.LaptopRepo;
import by.element.repos.ShopRepo;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import by.element.dateTimeService.DateParser;
import by.element.dateTimeService.DateTimeChecker;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class AvailabilityExcelImporter {
    private final LaptopRepo laptopRepo;
    private final ShopRepo shopRepo;
    private final DateParser dateParser;
    private final DateTimeChecker timeChecker;

    public AvailabilityExcelImporter(LaptopRepo laptopRepo, ShopRepo shopRepo,
                                     DateParser dateParser, DateTimeChecker timeChecker) {
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
        this.dateParser = dateParser;
        this.timeChecker = timeChecker;
    }

    @NotNull
    public List<Availability> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var availabilitySheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Модель", "цена", "Количество", "Номер магазина", "Адрес магазина",
                };
        if (TableValidator.isValidTableStructure(availabilitySheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newAvailabilities = new ArrayList<Availability>();

            var laptopColNum = 1;
            var priceColNum = 2;
            var quantityColNum = 3;
            var shopColNum = 5;
            var dateStartColNum = 6;
            var dateEndColNum = 7;

            for (Row row : availabilitySheet) {
                if (row.getRowNum() != 0) {
                    Laptop laptop = null;
                    int price = 0;
                    int quantity = 0;
                    Shop shop = null;
                    Date dateStart = null;
                    Date dateEnd = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == laptopColNum)
                            laptop = laptopRepo.findByLabelModel(cellValue);
                        else if (NumberUtils.isParsable(cellValue)) {
                            if (cell.getColumnIndex() == priceColNum)
                                price = Integer.parseInt(cellValue);
                            else if (cell.getColumnIndex() == quantityColNum)
                                quantity = Integer.parseInt(cellValue);
                        } else if (cell.getColumnIndex() == shopColNum && shopRepo.findByAddress(cellValue).size() != 0)
                            shop = shopRepo.findByAddress(cellValue).get(0);
                        else if (cell.getColumnIndex() == dateStartColNum)
                            try {
                                dateStart = dateParser.parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) {
                            }
                        else if (cell.getColumnIndex() == dateEndColNum)
                            try {
                                dateEnd = dateParser.parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) {
                            }
                    }
                    addNewAvailability(laptop, price, quantity, shop, dateStart, dateEnd, newAvailabilities);
                }
            }
            workbook.close();
            return newAvailabilities;
        } else
            throw new IllegalArgumentException();
    }

    private void addNewAvailability(Laptop laptop, int price, int quantity, Shop shop,
                                    Date dateStart, Date dateEnd, ArrayList<Availability> newAvailabilities) {
        if (laptop != null && price >= 5000 && quantity >= 1 && shop != null &&
                dateStart != null && dateEnd != null && !timeChecker.isDateStartPrevDateEnd(dateStart, dateEnd)) {
            var newAvailability = new Availability(quantity, price, shop, laptop);
            newAvailabilities.add(newAvailability);
        }
    }
}