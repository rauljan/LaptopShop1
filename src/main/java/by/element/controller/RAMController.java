package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import by.element.controllerService.RAMService;
import by.element.model.RAM;

@Controller
@RequestMapping("/ram")
public class RAMController {
    private final RAMService ramService;
    private Iterable<RAM> lastOutputtedRams;

    public RAMController(RAMService ramService) {
        this.ramService = ramService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @RequestParam(required = false) Integer price,
                             @NotNull Model siteModel) {
        var rams = ramService.loadRAMTable(model, memory, price);
        lastOutputtedRams = rams;
        siteModel.addAttribute("rams", rams);
        return "view/ram/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newRAM") RAM newRAM, @NotNull Model model) {
        var isNewRAMSaved = ramService.addRAMRecord(newRAM);
        if (!isNewRAMSaved) {
            model.addAttribute("errorMessage",
                    "Представленная новая модель оперативной памяти уже существует в базе!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel,
                             @RequestParam Integer editMemory,
                             @RequestParam Integer editPrice,
                             @NotNull @PathVariable RAM editRam, @NotNull Model model) {
        var isEditRAMSaved = ramService.editRANRecord(editModel, editMemory, editPrice, editRam);
        if (!isEditRAMSaved) {
            model.addAttribute("errorMessage",
                    "Представленная изменяемая модель оперативной памяти уже существует в базе!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = ramService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Загружен некорректный файл для таблицы оперативной памяти!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("rams", lastOutputtedRams);
        return "ramExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable RAM delRam) {
        ramService.deleteRecord(delRam);
        return "redirect:/ram";
    }
}