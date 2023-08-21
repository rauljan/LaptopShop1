package by.element.controllerService;

import by.element.model.RAM;
import by.element.repos.RAMRepo;
import by.element.specification.RAMSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.RAMExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class RAMService {
    private final RAMRepo ramRepo;

    private final RAMExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public RAMService(RAMRepo ramRepo, RAMExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.ramRepo = ramRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<RAM> loadRAMTable(String model, Integer memory, Integer price) {
        var ramSpecification = createRAMSpecification(model, memory, price);
        return ramRepo.findAll(ramSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<RAM> createRAMSpecification(String model, Integer memory, Integer price) {
        return Specification.where(RAMSpecification.modelLike(model))
                .and(RAMSpecification.memoryEqual(memory))
                .and(RAMSpecification.priceEqual(price));
    }

    public boolean addRAMRecord(RAM newRAM) {
        return saveRecord(newRAM);
    }

    public boolean editRANRecord(String editModel, Integer editMemory, Integer price, @NotNull RAM editRam) {
        editRam.setModel(editModel);
        editRam.setMemory(editMemory);
        return saveRecord(editRam);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var RAMFilePath = "";
        try {
            RAMFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newRAMs = excelImporter.importFile(RAMFilePath);
            newRAMs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(RAMFilePath);
            return false;
        }
    }

    private boolean saveRecord(RAM saveRAM) {
        try {
            ramRepo.save(saveRAM);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(RAM delRam) {
        ramRepo.delete(delRam);
    }
}