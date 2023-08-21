package by.element.controllerService;

import by.element.model.Basket;
import by.element.model.Buying;
import by.element.repos.BasketRepo;
import by.element.repos.BuyingRepo;
import by.element.repos.LaptopRepo;
import by.element.specification.BuyingSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.BuyingExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;
import java.time.LocalDateTime;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class BuyingService {
    private final BuyingRepo buyingRepo;

    private final BasketRepo basketRepo;
    private final LaptopRepo laptopRepo;

    private final BuyingExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public BuyingService(BuyingRepo buyingRepo, BasketRepo basketRepo, LaptopRepo laptopRepo,
                         BuyingExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.buyingRepo = buyingRepo;
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Buying> loadBuyingTable(Integer basketId, String laptopModel, Integer totalPrice,
                                            LocalDateTime dateTime, Model model) {
        var buyingSpecification = createBuyingSpecification(basketId, laptopModel, totalPrice, dateTime);
        var buyings = buyingRepo.findAll(buyingSpecification);
        initializeBuyingChoices(model);
        return buyings;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Buying> createBuyingSpecification(Integer basketId, String laptopModel, Integer totalPrice,
                                                            LocalDateTime dateTime) {
        return Specification.where(BuyingSpecification.basketIdEqual(basketId)).and(BuyingSpecification.laptopModelEqual(laptopModel))
                .and(BuyingSpecification.totalPriceEqual(totalPrice)).and(BuyingSpecification.dateTimeEqual(dateTime));
    }

    public void addBuyingRecord(Integer basketId, String laptopModel, Integer totalPrice) {
        Basket basket = null;
        if (basketRepo.findById(basketId).isPresent())
            basket = basketRepo.findById(basketId).get();
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var newBuying = new Buying(totalPrice, laptop, basket);
        buyingRepo.save(newBuying);
    }

    public void editBuyingRecord(@RequestParam Integer basketId, @RequestParam String laptopModel,
                                 @RequestParam Integer totalPrice, @PathVariable Buying editBuying) {
        Basket basket = null;
        if (basketRepo.findById(basketId).isPresent())
            basket = basketRepo.findById(basketId).get();
        editBuying.setBasket(basket);
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editBuying.setLaptop(laptop);
        editBuying.setTotalPrice(totalPrice);
        buyingRepo.save(editBuying);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeBuyingChoices(model);
        var buyingFilePath = "";
        try {
            buyingFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newBuyings = excelImporter.importFile(buyingFilePath);
            newBuyings.forEach(buyingRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(buyingFilePath);
            return false;
        }
    }

    public void deleteRecord(Buying delBuying) {
        buyingRepo.delete(delBuying);
    }

    private void initializeBuyingChoices(@NotNull Model model) {
        model.addAttribute("basketIds", basketRepo.getAllIds())
                .addAttribute("laptopModels", laptopRepo.getAllModels());
    }
}