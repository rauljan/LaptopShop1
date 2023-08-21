package by.element.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userBasket")
public class UserBasketController {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String re() {
        return "view/userBasket/blank";
    }

}
