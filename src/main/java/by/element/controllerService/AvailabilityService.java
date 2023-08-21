package by.element.controllerService;

import by.element.model.Availability;
import by.element.repos.AvailabilityRepo;
import by.element.repos.LaptopRepo;
import by.element.repos.ShopRepo;
import by.element.specification.AvailabilitySpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import by.element.dateTimeService.DateTimeChecker;
import by.element.excelInteraction.imports.AvailabilityExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class AvailabilityService {
    private final AvailabilityRepo availabilityRepo;
    private final DateTimeChecker timeChecker;

    private final LaptopRepo laptopRepo;
    private final ShopRepo shopRepo;

    private final AvailabilityExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public AvailabilityService(AvailabilityRepo availabilityRepo, DateTimeChecker timeChecker,
                               LaptopRepo laptopRepo, ShopRepo shopRepo, AvailabilityExcelImporter excelImporter,
                               UploadedFilesManager filesManager) {
        this.availabilityRepo = availabilityRepo;
        this.timeChecker = timeChecker;
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Availability> loadAvailabilityTable(Integer price, Integer quantity, String laptopModel,
                                                        String shopAddress, Model model) {
        var availabilitySpecification = createAvailabilitySpecification(
                price, quantity, laptopModel, shopAddress
               );
        var availabilities = availabilityRepo.findAll(availabilitySpecification);
        initializeAvailabilityChoices(model);
        return availabilities;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Availability> createAvailabilitySpecification(
            Integer price, Integer quantity, String laptopModel, String shopAddress) {
        return Specification.where(AvailabilitySpecification.fullPriceEqual(price)).and(AvailabilitySpecification.quantityEqual(quantity))
                .and(AvailabilitySpecification.laptopModelLike(laptopModel)).and(AvailabilitySpecification.shopAddressLike(shopAddress));
    }

    public boolean addAvailabilityRecord(Integer price, Integer quantity, String laptopModel, String shopAddress,
                                         Model model) {
        initializeAvailabilityChoices(model);
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        var newAvailability = new Availability(quantity, price,shop, laptop);
        return saveRecord(newAvailability);
    }

    public boolean editAvailabilityRecord(Integer price, Integer quantity, String laptopModel, String shopAddress,
                                          @NotNull Availability editAvailability,
                                          Model model) {
        initializeAvailabilityChoices(model);
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editAvailability.setLaptop(laptop);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        editAvailability.setShop(shop);
        editAvailability.setPrice(price);
        editAvailability.setQuantity(quantity);
        return saveRecord(editAvailability);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeAvailabilityChoices(model);
        var uploadedFilePath = "";
        try {
            uploadedFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newAvailabilities = excelImporter.importFile(uploadedFilePath);
            newAvailabilities.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(uploadedFilePath);
            return false;
        }
    }

    private boolean saveRecord(Availability saveAvailability) {
        try {
            availabilityRepo.save(saveAvailability);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
    }

    private void initializeAvailabilityChoices(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
    }
}