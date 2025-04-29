package com.wiseowl.bookstore.controller;

import com.wiseowl.bookstore.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;


@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("failMsg", "Registration failed! Please correct the errors.");
            return "register"; // stay on form and show validation + failMsg
        }

        // TODO: Save user to database here

        redirectAttributes.addFlashAttribute("successMsg", "Registration successful!");
        return "redirect:/login"; // redirect to login page after successful registration
    }
}

