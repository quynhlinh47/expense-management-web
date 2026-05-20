package com.quanlychitieu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quanlychitieu.model.Category;
import com.quanlychitieu.service.CategoryService;

@Controller
@RequestMapping("/categories")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // LIST

    @GetMapping
    public String list(Model model){

        model.addAttribute(
                "categories",
                categoryService.getAll()
        );

        model.addAttribute(
                "pageTitle",
                "Danh mục"
        );

        return "category/list";
    }

    // ADD

    @GetMapping("/add")
    public String add(Model model){

        model.addAttribute(
                "category",
                new Category()
        );

        model.addAttribute(
                "pageTitle",
                "Thêm danh mục"
        );

        return "category/add";
    }

    // SAVE

    @PostMapping("/save")
    public String save(
            @ModelAttribute Category category){

        categoryService.save(category);

        return "redirect:/categories";
    }

    // DELETE

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes){

        boolean deleted = categoryService.delete(id);

        if(deleted){

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Xoá danh mục thành công!"
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không thể xoá danh mục đang được sử dụng!"
            );
        }

        return "redirect:/categories";
    }

}