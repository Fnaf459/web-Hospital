package ru.ulstu.is.sbapp.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.user.model.User;
import ru.ulstu.is.sbapp.user.model.UserRole;
import ru.ulstu.is.sbapp.user.model.UserSignupDto;
import ru.ulstu.is.sbapp.user.service.UserService;
import ru.ulstu.is.sbapp.util.validation.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(UserSignupMvcController.SIGNUP_URL)
public class UserSignupMvcController {
    public static final String SIGNUP_URL = "/signup";

    private final UserService userService;

    public UserSignupMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("userDto", new UserSignupDto());
        List<UserRole> list = new ArrayList<UserRole>();
        list.add(UserRole.USER);
        list.add(UserRole.ADMIN);
        model.addAttribute("roles", list);
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("userDto") @Valid UserSignupDto userSignupDto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }
        try {
            final User user = userService.createUser(
                    userSignupDto.getLogin(), userSignupDto.getPassword(),
                    userSignupDto.getPasswordConfirm(), userSignupDto.getRole());
            return "redirect:/login?created=" + user.getLogin();
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
            return "signup";
        }
    }
}
