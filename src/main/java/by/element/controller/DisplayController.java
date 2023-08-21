package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import by.element.controllerService.DisplayService;
import by.element.model.Display;

@Controller
@RequestMapping("/display")
public class DisplayController {
    private final DisplayService displayService;
    private Iterable<Display> lastOutputtedDisplay;

    public DisplayController(DisplayService displayService) {
        this.displayService = displayService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String diagonal,
                             @RequestParam(required = false) String resolution,
                             @RequestParam(required = false) Integer price,
                             @NotNull Model siteModel) {
        var displays = displayService.loadDisplayTable(model, type, diagonal, resolution, price);
        lastOutputtedDisplay = displays;
        siteModel.addAttribute("displays", displays);
        return "view/display/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newDisplay") Display newDisplay, @NotNull Model model) {
        var isNewDisplaySaved = displayService.addDisplayRecord(newDisplay);
        if (!isNewDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Представленная новая модель дисплея уже существует в базе!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @PostMapping("/edit/{editDisplay}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel,
                             @RequestParam String editType,
                             @RequestParam String editDiagonal,
                             @RequestParam String editResolution,
                             @RequestParam Integer editPrice,
                             @NotNull @PathVariable Display editDisplay, @NotNull Model model) {
        var isEditDisplaySaved = displayService.editDisplayRecord(editModel, editType,
                editDiagonal, editResolution, editPrice, editDisplay);
        if (!isEditDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Представленная изменяемая модель дисплея уже существует в базе!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isEditDisplaySaved = displayService.importExcelRecords(uploadingFile);
        if (!isEditDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Загружен некорректный файл для таблицы дисплеев!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("displays", lastOutputtedDisplay);
        return "displayExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delDisplay}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Display delDisplay) {
        displayService.deleteRecord(delDisplay);
        return "redirect:/display";
    }


}