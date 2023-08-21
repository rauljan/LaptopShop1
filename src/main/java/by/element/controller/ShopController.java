package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import by.element.controllerService.ShopService;
import by.element.model.Shop;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private Iterable<Shop> lastOutputtedShops;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String address, @NotNull Model model) {
        var shops = shopService.loadShopTable(address);
        lastOutputtedShops = shops;
        model.addAttribute("shops", shops);
        return "view/shop/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newShop") Shop newShop, @NotNull Model model) {
        var isNewShopSaved = shopService.addShopRecord(newShop);
        if (!isNewShopSaved) {
            model.addAttribute("Представленный новый адрес магазина уже уже существует в базе!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editAddress, @NotNull @PathVariable Shop editShop,
                             @NotNull Model model) {
        var isEditShopSaved = shopService.editShopRecord(editAddress, editShop);
        if (!isEditShopSaved) {
            model.addAttribute("Представленный изменяемый адрес магазина уже существует в базе!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = shopService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Загружен некорректный файл для таблицы магазинов!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("shops", lastOutputtedShops);
        return "shopExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delShop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Shop delShop) {
        shopService.deleteRecord(delShop);
        return "redirect:/shop";
    }
}