package by.element.controllerService;

import by.element.model.Basket;
import by.element.model.Client;
import by.element.model.Employee;
import by.element.repos.BasketRepo;
import by.element.repos.ClientRepo;
import by.element.repos.EmployeeRepo;
import by.element.specification.BasketSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.BasketExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;
import java.time.LocalDateTime;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class BasketService {
    private final BasketRepo basketRepo;

    private final ClientRepo clientRepo;
    private final EmployeeRepo employeeRepo;

    private final BasketExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public BasketService(BasketRepo basketRepo, ClientRepo clientRepo, EmployeeRepo employeeRepo,
                         BasketExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.basketRepo = basketRepo;
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Basket> loadBasketTable(Integer employeeId, String employeeFirstName, String employeeSecondName,
                                            String employeeShopAddress, Integer clientId, String clientFirstName,
                                            String clientSecondName, LocalDateTime dateTime, Model model) {
        var basketSpecification = createBasketSpecification(employeeId, employeeFirstName, employeeSecondName,
                employeeShopAddress, clientId, clientFirstName, clientSecondName, dateTime);
        var baskets = basketRepo.findAll(basketSpecification);
        initializeBasketChoices(model);
        return baskets;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Basket> createBasketSpecification(
            Integer employeeId, String employeeFirstName, String employeeSecondName,
            String employeeShopAddress, Integer clientId, String clientFirstName,
            String clientSecondName, LocalDateTime dateTime) {
        return Specification.where(BasketSpecification.employeeIdEqual(employeeId))
                .and(BasketSpecification.clientIdEqual(clientId))
                .and(BasketSpecification.employeeFirstNameEqual(employeeFirstName))
                .and(BasketSpecification.employeeSecondNameEqual(employeeSecondName))
                .and(BasketSpecification.employeeShopAddressEqual(employeeShopAddress))
                .and(BasketSpecification.clientFirstNameEqual(clientFirstName))
                .and(BasketSpecification.clientSecondNameEqual(clientSecondName))
                .and(BasketSpecification.dateTimeEqual(dateTime));
    }

    public void addBasketRecord(@RequestParam Integer employeeId,
                                @RequestParam Integer clientId,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        Employee employee = null;
        if (employeeRepo.findById(employeeId).isPresent())
            employee = employeeRepo.findById(employeeId).get();

        Client client = null;
        if (clientRepo.findById(clientId).isPresent())
            client = clientRepo.findById(clientId).get();

        var newBasket = new Basket(dateTime, employee, client);
        basketRepo.save(newBasket);
    }

    public void editBasketRecord(Integer editEmployeeId, Integer editClientId,
                                 LocalDateTime editDateTime, Basket editBasket) {
        Employee employee = null;
        if (employeeRepo.findById(editEmployeeId).isPresent())
            employee = employeeRepo.findById(editEmployeeId).get();
        editBasket.setEmployee(employee);

        Client client = null;
        if (clientRepo.findById(editClientId).isPresent())
            client = clientRepo.findById(editClientId).get();
        editBasket.setClient(client);
        editBasket.setDateTime(editDateTime);
        basketRepo.save(editBasket);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeBasketChoices(model);
        var basketFilePath = "";
        try {
            basketFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newBaskets = excelImporter.importFile(basketFilePath);
            newBaskets.forEach(basketRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(basketFilePath);
            return false;
        }
    }

    public void deleteRecord(Basket delBasket) {
        basketRepo.delete(delBasket);
    }

    private void initializeBasketChoices(@NotNull Model model) {
        model.addAttribute("clientIds", clientRepo.getAllIds())
                .addAttribute("employeeIds", employeeRepo.getAllIds());
    }
}