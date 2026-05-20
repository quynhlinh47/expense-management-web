package com.quanlychitieu.controller;

import com.quanlychitieu.model.User;
import com.quanlychitieu.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================
    // ACCOUNT PAGE
    // =========================

    @GetMapping
    public String account(
            Model model,
            Authentication authentication
    ){

        // lấy email user đang login
        String email =
                authentication.getName();

        // lấy user từ database
        User user =
                userService.findByEmail(email);

        model.addAttribute("user", user);

        model.addAttribute(
                "pageTitle",
                "Tài khoản"
        );

        return "account/index";
    }

    // =========================
    // SAVE PROFILE
    // =========================

    @PostMapping("/save")
    public String save(

            @ModelAttribute User user,

            @RequestParam("avatarFile")
            MultipartFile file,

            Authentication authentication

    ) throws IOException {

        String email = authentication.getName();

        User oldUser =
                userService.findByEmail(email);

        if(oldUser == null){
            return "redirect:/account";
        }

        // giữ dữ liệu cũ
        user.setId(oldUser.getId());

        user.setPassword(oldUser.getPassword());

        user.setCreatedAt(oldUser.getCreatedAt());

        // =========================
        // UPLOAD AVATAR
        // =========================

        if(!file.isEmpty()){

            // thư mục uploads nằm ngoài project
            String uploadDir =
                    System.getProperty("user.dir")
                    + "/uploads/";

            File dir = new File(uploadDir);

            if(!dir.exists()){
                dir.mkdirs();
            }

            String fileName =
                    UUID.randomUUID()
                    + "_"
                    + file.getOriginalFilename();

            File saveFile =
                    new File(uploadDir + fileName);

            file.transferTo(saveFile);

            // lưu đường dẫn
            user.setAvatar("/uploads/" + fileName);

        } else {

            // giữ avatar cũ
            user.setAvatar(oldUser.getAvatar());
        }

        // lưu database
        userService.save(user);

        return "redirect:/account";
    }

    // =========================
    // CHANGE PASSWORD
    // =========================

    @PostMapping("/change-password")
    public String changePassword(

            @RequestParam String oldPassword,

            @RequestParam String newPassword,

            @RequestParam String confirmPassword,

            RedirectAttributes redirectAttributes,

            Authentication authentication

    ){

        User user =
                userService.findByEmail(
                        authentication.getName()
                );

        if(user == null){

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không tìm thấy tài khoản!"
            );

            return "redirect:/account";
        }

        // kiểm tra mật khẩu cũ

        if(!passwordEncoder.matches(
                oldPassword,
                user.getPassword()
        )){

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Mật khẩu cũ không đúng!"
            );

            return "redirect:/account";
        }

        // kiểm tra xác nhận

        if(!newPassword.equals(confirmPassword)){

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Xác nhận mật khẩu không khớp!"
            );

            return "redirect:/account";
        }

        // mã hóa password mới

        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userService.save(user);

        redirectAttributes.addFlashAttribute(
                "success",
                "Đổi mật khẩu thành công!"
        );

        return "redirect:/account";
    }

}