package by.element.controllerService;

import by.element.model.Type;
import by.element.repos.TypeRepo;
import by.element.specification.TypeSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.TypeExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class TypeService {
    private final TypeRepo typeRepo;

    private final TypeExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public TypeService(TypeRepo typeRepo, TypeExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.typeRepo = typeRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Type> loadTypeTable(String typeName) {
        var typeSpecification = createTypeSpecification(typeName);
        return typeRepo.findAll(typeSpecification);
    }

    private Specification<Type> createTypeSpecification(String typeName) {
        return Specification.where(TypeSpecification.typeNameLike(typeName));
    }

    public boolean addTypeRecord(Type newType) {
        return saveRecord(newType);
    }

    public boolean editTypeRecord(String name, @NotNull @PathVariable Type editType) {
        editType.setName(name);
        return saveRecord(editType);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var typeFilePath = "";
        try {
            typeFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newTypes = excelImporter.importFile(typeFilePath);
            newTypes.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(typeFilePath);
            return false;
        }
    }

    private boolean saveRecord(Type saveType) {
        try {
            typeRepo.save(saveType);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Type delType) {
        typeRepo.delete(delType);
    }
}