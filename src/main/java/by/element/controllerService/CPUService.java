package by.element.controllerService;

import by.element.model.CPU;
import by.element.repos.CPURepo;
import by.element.specification.CPUSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.CPUExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class CPUService {
    private final CPURepo cpuRepo;

    private final CPUExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public CPUService(CPURepo cpuRepo, CPUExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.cpuRepo = cpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<CPU> loadCPUTable(String model, String frequency, Integer price) {
        var cpuSpecification = createCPUSpecification(model, frequency, price);
        return cpuRepo.findAll(cpuSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<CPU> createCPUSpecification(String model, String frequency, Integer price) {
        return Specification.where(CPUSpecification.modelLike(model))
                .and(CPUSpecification.frequencyEqual(frequency))
                .and(CPUSpecification.priceEqual(price));
    }

    public boolean addCPURecord(CPU newCPU) {
        return saveRecord(newCPU);
    }

    public boolean editCPURecord(String editModel, String editFrequency, Integer price, @NotNull CPU editCpu) {
        editCpu.setModel(editModel);
        editCpu.setFrequency(editFrequency);
        editCpu.setPrice(price);
        return saveRecord(editCpu);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var cpuFilePath = "";
        try {
            cpuFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newCPUs = excelImporter.importFile(cpuFilePath);
            newCPUs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(cpuFilePath);
            return false;
        }
    }

    private boolean saveRecord(CPU saveCPU) {
        try {
            cpuRepo.save(saveCPU);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(CPU delCpu) {
        cpuRepo.delete(delCpu);
    }
}