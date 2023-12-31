package by.element.excelInteraction.imports;

import by.element.model.Hardware;
import by.element.model.Label;
import by.element.model.Laptop;
import by.element.model.Type;
import by.element.repos.HardwareRepo;
import by.element.repos.LabelRepo;
import by.element.repos.TypeRepo;
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
public class LaptopExcelImporter {
    private final LabelRepo labelRepo;
    private final TypeRepo typeRepo;
    private final HardwareRepo hardwareRepo;

    public LaptopExcelImporter(LabelRepo labelRepo, TypeRepo typeRepo, HardwareRepo hardwareRepo) {
        this.labelRepo = labelRepo;
        this.typeRepo = typeRepo;
        this.hardwareRepo = hardwareRepo;
    }

    @NotNull
    public List<Laptop> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var laptopSheet = workbook.getSheetAt(0);

        var laptopTableFields = new String[]{"Id", "Бренд", "Модель", "Тип", "Сборка"};
        if (TableValidator.isValidTableStructure(laptopSheet, laptopTableFields)) {
            var dataFormatter = new DataFormatter();
            var newLaptops = new ArrayList<Laptop>();

            var labelColNum = 2;
            var typeColNum = 3;
            var hardwareColNum = 4;

            for (Row row : laptopSheet) {
                if (row.getRowNum() != 0) {
                    Label label = null;
                    Type type = null;
                    Hardware hardware = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == labelColNum)
                            label = labelRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == typeColNum && typeRepo.findByName(cellValue).size() != 0)
                            type = typeRepo.findByName(cellValue).get(0);
                        else if (cell.getColumnIndex() == hardwareColNum)
                            hardware = hardwareRepo.findByAssemblyName(cellValue);
                    }
                    addNewLaptop(label, type, hardware, newLaptops);
                }
            }
            workbook.close();
            return newLaptops;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewLaptop(Label label, Type type, Hardware hardware, ArrayList<Laptop> newLaptops) {
        if (label != null && type != null && hardware != null) {
            var newLaptop = new Laptop(label, type, hardware);
            newLaptops.add(newLaptop);
        }
    }
}