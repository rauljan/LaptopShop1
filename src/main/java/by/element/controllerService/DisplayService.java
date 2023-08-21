package by.element.controllerService;

import by.element.model.Display;
import by.element.repos.DisplayRepo;
import by.element.specification.DisplaySpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.DisplayExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class DisplayService {
    private final DisplayRepo displayRepo;

    private final DisplayExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public DisplayService(DisplayRepo displayRepo, DisplayExcelImporter excelImporter,
                          UploadedFilesManager filesManager) {
        this.displayRepo = displayRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Display> loadDisplayTable(String model, String type, String diagonal, String resolution, Integer price) {
        var displaySpecification = createDisplaySpecification(model, type, diagonal, resolution, price);
        return displayRepo.findAll(displaySpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Display> createDisplaySpecification(String model, String type,
                                                              String diagonal, String resolution, Integer price) {
        return Specification.where(DisplaySpecification.modelLike(model))
                .and(DisplaySpecification.typeEqual(type))
                .and(DisplaySpecification.diagonalEqual(diagonal))
                .and(DisplaySpecification.resolutionEqual(resolution))
                .and(DisplaySpecification.priceEqual(price));
    }

    public boolean addDisplayRecord(Display newDisplay) {
        return saveRecord(newDisplay);
    }

    public boolean editDisplayRecord(String model, String type, String diagonal,
                                     String resolution, Integer price, @NotNull Display editDisplay) {
        editDisplay.setModel(model);
        editDisplay.setType(type);
        editDisplay.setDiagonal(diagonal);
        editDisplay.setResolution(resolution);
        editDisplay.setPrice(price);
        return saveRecord(editDisplay);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var displayFilePath = "";
        try {
            displayFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newDisplays = excelImporter.importFile(displayFilePath);
            newDisplays.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(displayFilePath);
            return false;
        }
    }

    private boolean saveRecord(Display saveDisplay) {
        try {
            displayRepo.save(saveDisplay);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Display delDisplay) {
        displayRepo.delete(delDisplay);
    }

}