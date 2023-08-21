package by.element.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import by.element.controllerService.UserService;
import by.element.security.User;

import static by.element.security.Role.USER;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @NotNull
    @GetMapping
    private String registration() {
        return "/security/registration";
    }

    @NotNull
    @PostMapping
    private String registration(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        newUser.setRole(USER);
        var isNewUserSaved = userService.addUserRecord(newUser, model);
        if (!isNewUserSaved) {
            model.addAttribute("errorMessage",
                    "Представленный новый логин или e-mail уже существует в базе данных!");
            return "/security/registration";
        }
        model.addAttribute("activationMessage",
                "Ожидайте письмо со ссылкой на активацию вашей учетной записи " +
                        "по почте " + newUser.getEmail() + ".");
        return "/security/login";
    }

    @NotNull
    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        var activationUser = userService.activateUser(code);
        if (activationUser == null) {
            model.addAttribute("errorMessage", "Код активации не был найден!");
            return "/security/registration";
        }
        model.addAttribute("activationMessage",
                "Пользователь " + activationUser.getUsername() + " активован успешно!");
        return "/security/login";
    }
}