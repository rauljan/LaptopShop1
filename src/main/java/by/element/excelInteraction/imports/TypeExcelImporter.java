package by.element.excelInteraction.imports;

import by.element.model.Type;
import by.element.inputService.InputValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeExcelImporter {
    @NotNull
    public List<Type> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var typeSheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Название"};
        if (TableValidator.isValidTableStructure(typeSheet, typeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newTypes = new ArrayList<Type>();

            var nameColNum = 1;

            for (Row row : typeSheet) {
                String typeName = null;

                if (row.getRowNum() != 0) {
                    for (Cell cell : row)
                        if (cell.getColumnIndex() == nameColNum)
                            typeName = dataFormatter.formatCellValue(cell);

                    addNewType(typeName, newTypes);
                }
            }
            return newTypes;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewType(String name, ArrayList<Type> newTypes) {
        if (InputValidator.stringContainsAlphabet(name)) {
            var newType = new Type(name);
            newTypes.add(newType);
        }
    }
}