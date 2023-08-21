package by.element.controllerService;

import by.element.model.HDD;
import by.element.repos.HDDRepo;
import by.element.specification.HDDSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.HDDExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class HDDService {
    private final HDDRepo hddRepo;

    private final HDDExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public HDDService(HDDRepo hddRepo, HDDExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.hddRepo = hddRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<HDD> loadHDDTable(String model, Integer memory, Integer price) {
        var hddSpecification = createHDDSpecification(model, memory, price);
        return hddRepo.findAll(hddSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<HDD> createHDDSpecification(String model, Integer memory, Integer price) {
        return Specification.where(HDDSpecification.modelLike(model))
                .and(HDDSpecification.memoryEqual(memory))
                .and(HDDSpecification.priceEqual(price));
    }

    public boolean addHDDRecord(HDD newHDD) {
        return saveRecord(newHDD);
    }

    public boolean editHDDRecord(String model, Integer memory, Integer price, @NotNull HDD editHDD) {
        editHDD.setModel(model);
        editHDD.setMemory(memory);
        editHDD.setPrice(price);
        return saveRecord(editHDD);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var HDDFilePath = "";
        try {
            HDDFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newHDDs = excelImporter.importFile(HDDFilePath);
            newHDDs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(HDDFilePath);
            return false;
        }
    }

    private boolean saveRecord(HDD saveHDD) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(HDD delHDD) {
        hddRepo.delete(delHDD);
    }
}