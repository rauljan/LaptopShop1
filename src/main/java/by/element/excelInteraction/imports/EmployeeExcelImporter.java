package by.element.excelInteraction.imports;

import by.element.model.Employee;
import by.element.model.Shop;
import by.element.inputService.InputValidator;
import by.element.repos.ShopRepo;
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
public class EmployeeExcelImporter {
    private ShopRepo shopRepo;

    public EmployeeExcelImporter(ShopRepo shopRepo) {
        this.shopRepo = shopRepo;
    }

    @NotNull
    public List<Employee> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var employeeSheet = workbook.getSheetAt(0);

        var employeeTableFields = new String[]{"Id", "Имя", "Фамилия", "№ магазина", "Адрес магазина", "Статус"};
        if (TableValidator.isValidTableStructure(employeeSheet, employeeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newEmployees = new ArrayList<Employee>();

            var firstNameColNum = 1;
            var secondNameColNum = 2;
            var shopColNum = 4;
            var isActiveColNum = 5;

            for (Row row : employeeSheet) {
                if (row.getRowNum() != 0) {
                    String firstName = null;
                    String secondName = null;
                    Shop shop = null;
                    boolean isActive = false;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == firstNameColNum)
                            firstName = cellValue;
                        else if (cell.getColumnIndex() == secondNameColNum)
                            secondName = cellValue;
                        else if (cell.getColumnIndex() == shopColNum && shopRepo.findByAddress(cellValue).size() != 0)
                            shop = shopRepo.findByAddress(cellValue).get(0);
                        else if (cell.getColumnIndex() == isActiveColNum)
                            isActive = cellValue.equalsIgnoreCase("работает");
                    }
                    addNewEmployee(firstName, secondName, shop, isActive, newEmployees);
                }
            }
            workbook.close();
            return newEmployees;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewEmployee(String firstName, String secondName, Shop shop, boolean isActive,
                                       ArrayList<Employee> newEmployees) {
        if (InputValidator.stringContainsAlphabet(firstName) && InputValidator.stringContainsAlphabet(secondName) && shop != null) {
            var newEmployee = new Employee(firstName, secondName, shop, isActive);
            newEmployees.add(newEmployee);
        }
    }
}