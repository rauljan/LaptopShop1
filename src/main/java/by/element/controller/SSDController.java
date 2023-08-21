package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import by.element.controllerService.SSDService;
import by.element.model.SSD;

@Controller
@RequestMapping("/ssd")
public class SSDController {
    private final SSDService ssdService;
    private Iterable<SSD> lastOutputtedSSDs;

    public SSDController(SSDService ssdService) {
        this.ssdService = ssdService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @RequestParam(required = false) Integer price,
                             @NotNull Model siteModel) {
        var ssds = ssdService.loadSSDTable(model, memory, price);
        lastOutputtedSSDs = ssds;
        siteModel.addAttribute("ssds", ssds);
        return "view/ssd/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newSSD") SSD newSSD, @NotNull Model model) {
        var isNewSSDSaved = ssdService.addSSDRecord(newSSD);
        if (!isNewSSDSaved) {
            model.addAttribute("errorMessage",
                    "Представленная новая модель SSD диска уже существует в базе!");
            model.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @PostMapping("/edit/{editSSD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel,
                             @RequestParam Integer editMemory,
                             @RequestParam Integer editPrice,
                             @NotNull @PathVariable SSD editSSD, @NotNull Model siteModel) {
        var isEditSSDSaved = ssdService.editSSDRecord(editModel, editMemory, editPrice, editSSD);
        if (!isEditSSDSaved) {
            siteModel.addAttribute("errorMessage",
                    "Представленная изменяемая модель SSD диска уже существует в базе!");
            siteModel.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = ssdService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Загружен некорректный файл для таблицы SSD дисков!");
            model.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("ssds", lastOutputtedSSDs);
        return "ssdExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delSSD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable SSD delSSD) {
        ssdService.deleteRecord(delSSD);
        return "redirect:/ssd";
    }


}