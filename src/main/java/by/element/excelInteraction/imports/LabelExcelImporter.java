package by.element.excelInteraction.imports;

import by.element.model.Label;
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
public class LabelExcelImporter {
    @NotNull
    public List<Label> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var labelSheet = workbook.getSheetAt(0);

        var labelTableFields = new String[]{"Id", "Бренд", "Модель"};
        if (TableValidator.isValidTableStructure(labelSheet, labelTableFields)) {
            var dataFormatter = new DataFormatter();
            var newLabels = new ArrayList<Label>();

            var brandColNum = 1;
            var modelColNum = 2;

            for (Row row : labelSheet) {
                if (row.getRowNum() != 0) {
                    String brand = null;
                    String model = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == brandColNum)
                            brand = cellValue;
                        else if (cell.getColumnIndex() == modelColNum)
                            model = cellValue;
                    }
                    addNewLabel(brand, model, newLabels);
                }
            }
            workbook.close();
            return newLabels;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewLabel(String brand, String model, ArrayList<Label> newLabels) {
        if (InputValidator.stringContainsAlphabet(brand) && InputValidator.stringContainsAlphabet(model)) {
            var newLabel = new Label(brand, model);
            newLabels.add(newLabel);
        }
    }
}