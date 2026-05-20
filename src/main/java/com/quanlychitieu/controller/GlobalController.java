package com.quanlychitieu.controller;

import com.quanlychitieu.model.User;
import com.quanlychitieu.service.UserService;
import com.quanlychitieu.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;

    @ModelAttribute
    public void globalUser(
            Authentication authentication,
            Model model
    ){

        if(authentication != null){

            String email =
                    authentication.getName();

            User user =
                    userService.findByEmail(email);

            model.addAttribute(
                    "currentUser",
                    user
            );
            
            model.addAttribute(
                    "notifications",
                    notificationService.getLatest(user)
            );

            model.addAttribute(
                    "unreadCount",
                    notificationService.countUnread(user)
            );
        }
    }
}