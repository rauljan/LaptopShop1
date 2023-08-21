package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import by.element.controllerService.AvailabilityService;
import by.element.model.Availability;

@Controller
@RequestMapping("/availability")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private Iterable<Availability> lastOutputtedAvailabilities;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) Integer price,
                             @RequestParam(required = false) Integer quantity,
                             @RequestParam(required = false) String laptopModel,
                             @RequestParam(required = false) String shopAddress,
                             @NotNull Model model) {
        var availabilities = availabilityService.loadAvailabilityTable(price, quantity, laptopModel, shopAddress,model);
        lastOutputtedAvailabilities = availabilities;
        model.addAttribute("availabilities", availabilities);
        return "view/availability/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                            @RequestParam String laptopModel, @RequestParam String shopAddress,
                            @NotNull Model model) {
        var isNewAvailabilitySaved = availabilityService.addAvailabilityRecord(price, quantity, laptopModel, shopAddress,model);
        if (!isNewAvailabilitySaved) {
            model.addAttribute("errorMessage",
                    "Представленная новая модель ноутбука уже существует в записях о наличии!");
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
        return "redirect:/availability";
    }

    @NotNull
    @PostMapping("/edit/{editAvailability}")
    public String editRecord(@RequestParam Integer editPrice, @RequestParam Integer editQuantity,
                             @RequestParam String editLaptopModel, @RequestParam String editShopAddress,
                             @NotNull @PathVariable Availability editAvailability, @NotNull Model model) {
        var isEditAvailabilitySaved = availabilityService.editAvailabilityRecord(
                editPrice, editQuantity, editLaptopModel, editShopAddress,
                editAvailability, model);
        if (!isEditAvailabilitySaved) {
            model.addAttribute("errorMessage",
                    "Представленная изменяемая модель ноутбука уже существует в записях о наличии!");
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
        return "redirect:/availability";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = availabilityService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Загружен некорректный файл таблицы записей о наличии!");
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("availabilities", lastOutputtedAvailabilities);
        return "availabilityExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delAvailability}")
    public String deleteRecord(@NotNull @PathVariable Availability delAvailability) {
        availabilityService.deleteRecord(delAvailability);
        return "redirect:/availability";
    }
}