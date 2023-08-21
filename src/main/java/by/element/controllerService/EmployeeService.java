package by.element.controllerService;

import by.element.model.Employee;
import by.element.repos.EmployeeRepo;
import by.element.repos.ShopRepo;
import by.element.specification.EmployeeSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import by.element.excelInteraction.imports.EmployeeExcelImporter;
import by.element.excelInteraction.imports.UploadedFilesManager;

import java.io.IOException;

import static by.element.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;

@Service
@Lazy
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    private final ShopRepo shopRepo;

    private final EmployeeExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public EmployeeService(EmployeeRepo employeeRepo, ShopRepo shopRepo, EmployeeExcelImporter excelImporter,
                           UploadedFilesManager filesManager) {
        this.employeeRepo = employeeRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Employee> loadEmployeeTable(String firstName, String secondName, String shopAddress,
                                                String isWorking, Model model) {
        var employeesSpecification = createEmployeeSpecification(firstName, secondName, shopAddress, isWorking);
        var employees = employeeRepo.findAll(employeesSpecification);
        initEmployeeChoices(model);
        return employees;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Employee> createEmployeeSpecification(String firstName, String secondName,
                                                                String shopAddress, String isWorking) {
        return Specification.where(EmployeeSpecification.firstNameEqual(firstName)).and(EmployeeSpecification.secondNameEqual(secondName))
                .and(EmployeeSpecification.shopAddressLike(shopAddress)).and(EmployeeSpecification.isWorkingEqual(isWorking));
    }

    public void addEmployeeRecord(String firstName, String secondName, String shopAddress) {
        var shop = shopAddress != null ? shopRepo.findByAddress(shopAddress).get(0) : null;
        var newEmployee = new Employee(firstName, secondName, shop, true);
        employeeRepo.save(newEmployee);
    }

    public void editEmployeeRecord(String firstName, String secondName, String shopAddress,
                                   @NotNull String isWorking, @NotNull Employee editEmployee) {
        editEmployee.setFirstName(firstName);
        editEmployee.setSecondName(secondName);
        var employeeShop = shopRepo.findByAddress(shopAddress).get(0);
        editEmployee.setShop(employeeShop);
        editEmployee.setActive(isWorking.equals("Работает"));
        employeeRepo.save(editEmployee);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initEmployeeChoices(model);
        var employeeFilePath = "";
        try {
            employeeFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newEmployees = excelImporter.importFile(employeeFilePath);
            newEmployees.forEach(employeeRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(employeeFilePath);
            return false;
        }
    }

    public void deleteRecord(Employee delEmployee) {
        employeeRepo.delete(delEmployee);
    }

    private void initEmployeeChoices(@NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("shopAddresses", shopsAddresses);
    }
}