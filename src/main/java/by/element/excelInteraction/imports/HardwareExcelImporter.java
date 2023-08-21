package by.element.excelInteraction.imports;

import by.element.model.*;
import by.element.inputService.InputValidator;
import by.element.repos.*;
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

import static by.element.excelInteraction.imports.TableValidator.isValidTableStructure;

@Service
@Lazy
public class HardwareExcelImporter {
    private CPURepo cpuRepo;
    private RAMRepo ramRepo;
    private SSDRepo ssdRepo;
    private DisplayRepo displayRepo;
    private HDDRepo hddRepo;
    private GPURepo gpuRepo;

    public HardwareExcelImporter(CPURepo cpuRepo, RAMRepo ramRepo, SSDRepo ssdRepo,
                                 DisplayRepo displayRepo, HDDRepo hddRepo, GPURepo gpuRepo) {
        this.cpuRepo = cpuRepo;
        this.ramRepo = ramRepo;
        this.ssdRepo = ssdRepo;
        this.displayRepo = displayRepo;
        this.hddRepo = hddRepo;
        this.gpuRepo = gpuRepo;
    }

    @NotNull
    public List<Hardware> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var hardwareSheet = workbook.getSheetAt(0);

        var hardwareTableFields = new String[]{"Id", "Название сборки", "Модель процесора", "Модель видеокарты",
                "Модель дисплея", "Модель оперативной памяти", "Модель SSD диска", "Модель HDD диска"};
        if (isValidTableStructure(hardwareSheet, hardwareTableFields)) {
            var dataFormatter = new DataFormatter();
            var newHardware = new ArrayList<Hardware>();

            var assemblyNameColNum = 1;
            var cpuColNum = 2;
            var gpuColNum = 3;
            var displayColNum = 4;
            var ramColNum = 5;
            var ssdColNum = 6;
            var hddColNum = 7;
            var priceColNum = 8;

            for (Row row : hardwareSheet) {
                if (row.getRowNum() != 0) {
                    String assemblyName = null;
                    CPU cpu = null;
                    GPU gpu = null;
                    Display display = null;
                    RAM ram = null;
                    SSD ssd = null;
                    HDD hdd = null;
                    int currPrice = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == assemblyNameColNum)
                            assemblyName = cellValue;
                        else if (cell.getColumnIndex() == cpuColNum)
                            cpu = cpuRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == gpuColNum)
                            gpu = gpuRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == displayColNum)
                            display = displayRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == ramColNum)
                            ram = ramRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == ssdColNum)
                            ssd = ssdRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == hddColNum)
                            hdd = hddRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == priceColNum && NumberUtils.isParsable(cellValue))
                            currPrice = Integer.parseInt(cellValue);
                    }
                    addNewHardware(assemblyName, cpu, gpu, display, ram, ssd, hdd, currPrice, newHardware);
                }
            }
            workbook.close();
            return newHardware;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewHardware(String assemblyName, CPU cpu, GPU gpu, Display display, RAM ram, SSD ssd, HDD hdd,
                                       Integer price,
                                       ArrayList<Hardware> newHardware) {
        if (InputValidator.stringContainsAlphabet(assemblyName) && cpu != null && gpu != null &&
                display != null && ram != null && ssd != null && hdd != null) {
            var newAssembly = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, price, display);
            newHardware.add(newAssembly);
        }
    }
}
