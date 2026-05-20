package com.quanlychitieu.controller;

import com.quanlychitieu.model.User;
import com.quanlychitieu.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // LOGIN

    @GetMapping("/login")
    public String login(){

        return "auth/login";
    }

    // REGISTER

    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute(
                "user",
                new User()
        );

        return "auth/register";
    }

    @PostMapping("/save-register")
    public String saveRegister(
            @ModelAttribute User user
    ){

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        userService.save(user);

        return "redirect:/login";
    }
}