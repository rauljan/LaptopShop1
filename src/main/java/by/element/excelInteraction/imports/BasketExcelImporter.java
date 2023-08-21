package by.element.excelInteraction.imports;

import by.element.model.Basket;
import by.element.model.Client;
import by.element.model.Employee;
import by.element.repos.ClientRepo;
import by.element.repos.EmployeeRepo;
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
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class BasketExcelImporter {
    private EmployeeRepo employeeRepo;
    private ClientRepo clientRepo;

    public BasketExcelImporter(EmployeeRepo employeeRepo, ClientRepo clientRepo) {
        this.employeeRepo = employeeRepo;
        this.clientRepo = clientRepo;
    }

    @NotNull
    public List<Basket> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var basketSheet = workbook.getSheetAt(0);

        var basketTableFields = new String[]{"Id", "Id продавца", "Имя продавца", "Фамилия продавца",
                "Адрес магазина", "Id покупателя", "Имя покупателя", "Фамилия покупателя", "Время покупки"};
        if (TableValidator.isValidTableStructure(basketSheet, basketTableFields)) {
            var dataFormatter = new DataFormatter();
            var newBaskets = new ArrayList<Basket>();

            var employeeColNum = 1;
            var clientColNum = 5;
            var dateTimeColNum = 8;

            for (Row row : basketSheet) {
                if (row.getRowNum() != 0) {
                    Employee employee = null;
                    Client client = null;
                    LocalDateTime dateTime = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (NumberUtils.isParsable(cellValue)) {
                            if (cell.getColumnIndex() == employeeColNum) {
                                var employeeId = Integer.parseInt(cellValue);
                                if (employeeRepo.findById(employeeId).isPresent())
                                    employee = employeeRepo.findById(employeeId).get();
                            } else if (cell.getColumnIndex() == clientColNum) {
                                var clientId = Integer.parseInt(cellValue);
                                if (clientRepo.findById(clientId).isPresent())
                                    client = clientRepo.findById(clientId).get();
                            }
                        } else if (cell.getColumnIndex() == dateTimeColNum)
                            try {
                                var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
                                dateTime = LocalDateTime.parse(cellValue, dateTimeFormat);
                            } catch (DateTimeException | ArrayIndexOutOfBoundsException ignored) {
                            }
                    }
                    addNewBasket(employee, client, dateTime, newBaskets);
                }
            }
            workbook.close();
            return newBaskets;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewBasket(Employee employee, Client client, LocalDateTime dateTime,
                                     ArrayList<Basket> newBaskets) {
        if (employee != null && client != null && dateTime != null) {
            var newBasket = new Basket(dateTime, employee, client);
            newBaskets.add(newBasket);
        }
    }
}