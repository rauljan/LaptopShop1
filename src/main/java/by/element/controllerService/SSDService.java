package by.element.controllerService;

import by.element.model.SSD;
import by.element.repos.SSDRepo;
import by.element.specification.SSDSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.SSDExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class SSDService {
    private final SSDRepo ssdRepo;

    private final SSDExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public SSDService(SSDRepo ssdRepo, SSDExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.ssdRepo = ssdRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<SSD> loadSSDTable(String model, Integer memory, Integer price) {
        var ssdSpecification = createSSDSpecification(model, memory, price);
        return ssdRepo.findAll(ssdSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<SSD> createSSDSpecification(String model, Integer memory, Integer price) {
        return Specification.where(SSDSpecification.modelLike(model))
                .and(SSDSpecification.memoryEqual(memory))
                .and(SSDSpecification.priceEqual(price));
    }

    public boolean addSSDRecord(SSD newSSD) {
        return saveRecord(newSSD);
    }

    public boolean editSSDRecord(String model, Integer memory, Integer price, @NotNull SSD editSSD) {
        editSSD.setModel(model);
        editSSD.setMemory(memory);
        editSSD.setPrice(price);
        return saveRecord(editSSD);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var SSDFilePath = "";
        try {
            SSDFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newSSDs = excelImporter.importFile(SSDFilePath);
            newSSDs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(SSDFilePath);
            return false;
        }
    }

    private boolean saveRecord(SSD saveSSD) {
        try {
            ssdRepo.save(saveSSD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(SSD delSSD) {
        ssdRepo.delete(delSSD);
    }
}