package by.element.controllerService;

import by.element.model.GPU;
import by.element.repos.GPURepo;
import by.element.specification.GPUSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.GPUExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class GpuService {
    private final GPURepo gpuRepo;

    private final GPUExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public GpuService(GPURepo gpuRepo, GPUExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.gpuRepo = gpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<GPU> loadGPUTable(String model, Integer memory, Integer price) {
        var gpuSpecification = createCPUSpecification(model, memory, price);
        return gpuRepo.findAll(gpuSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<GPU> createCPUSpecification(String model, Integer memory, Integer price) {
        return Specification.where(GPUSpecification.modelLike(model))
                .and(GPUSpecification.memoryEqual(memory))
                .and(GPUSpecification.priceEqual(price));
    }

    public boolean addGPURecord(GPU newGPU) {
        return saveRecord(newGPU);
    }

    public boolean editGPURecord(String model, Integer memory, Integer price, @NotNull GPU editGpu) {
        editGpu.setModel(model);
        editGpu.setMemory(memory);
        editGpu.setPrice(price);
        return saveRecord(editGpu);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var GPUFilePath = "";
        try {
            GPUFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newGPUs = excelImporter.importFile(GPUFilePath);
            newGPUs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(GPUFilePath);
            return false;
        }
    }

    private boolean saveRecord(GPU saveGpu) {
        try {
            gpuRepo.save(saveGpu);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(GPU delGpu) {
        gpuRepo.delete(delGpu);
    }
}