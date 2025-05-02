package com.wiseowl.bookstore.controller;

import com.wiseowl.bookstore.dto.UserDto;
import com.wiseowl.bookstore.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRegisterService userRegisterService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // Validate the user input
        if (result.hasErrors()) {
            model.addAttribute("failMsg", "Registration failed! Please correct the errors.");
            return "register"; // stay on form and show validation + failMsg
        } else if (userRegisterService.userExists(userDto.getEmail())) {
            result.rejectValue("email", null, "Email already in use");
            return "register";
        }
        // save user to database
        userRegisterService.saveUser(userDto);
        logger.info("New user registered: {}", userDto.getEmail());
        redirectAttributes.addFlashAttribute("successMsg", "Registration successful! Please login.");
        return "redirect:/login"; // redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Ensure this is mapped to your login.html Thymeleaf template
    }
}









