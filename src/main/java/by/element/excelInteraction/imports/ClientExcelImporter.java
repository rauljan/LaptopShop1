package by.element.excelInteraction.imports;

import by.element.model.Client;
import by.element.inputService.InputValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import by.element.dateTimeService.DateParser;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class ClientExcelImporter {
    private final DateParser dateParser;

    public ClientExcelImporter(DateParser dateParser) {
        this.dateParser = dateParser;
    }

    @NotNull
    public List<Client> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var clientSheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Имя", "Фамилия", "Дата регистрации"};
        if (TableValidator.isValidTableStructure(clientSheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newClients = new ArrayList<Client>();

            var firstNameColNum = 1;
            var secondNameColNum = 2;
            var dateRegColNum = 3;

            for (Row row : clientSheet) {
                if (row.getRowNum() != 0) {
                    String firstName = null;
                    String secondName = null;
                    Date dateReg = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == firstNameColNum)
                            firstName = cellValue;
                        else if (cell.getColumnIndex() == secondNameColNum)
                            secondName = cellValue;
                        else if (cell.getColumnIndex() == dateRegColNum)
                            try {
                                dateReg = dateParser.parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) {
                            }
                    }
                    addNewClient(firstName, secondName, dateReg, newClients);
                }
            }
            workbook.close();
            return newClients;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewClient(String firstName, String secondName, Date dateReg,
                                     ArrayList<Client> newClients) {
        if (InputValidator.stringContainsAlphabet(firstName) && InputValidator.stringContainsAlphabet(secondName) && dateReg != null) {
            var newClient = new Client(firstName, secondName, dateReg);
            newClients.add(newClient);
        }
    }
}