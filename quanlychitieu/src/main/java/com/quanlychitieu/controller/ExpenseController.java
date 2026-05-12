package com.quanlychitieu.controller;

import com.quanlychitieu.model.Expense;
import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;
    
    @Autowired
    private CategoryService categoryService;

    // HIỂN THỊ DANH SÁCH

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "expenses",
                service.getAll()
        );

        return "expense/list";
    }

    // MỞ FORM THÊM

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute(
                "expense",
                new Expense()
        );
        
        model.addAttribute(
                "categories",
                categoryService.getAll()
        );

        return "expense/add";
    }

    // LƯU DỮ LIỆU

    @PostMapping("/save")
    public String save(
            @ModelAttribute Expense expense
    ) {

        service.save(expense);

        return "redirect:/expenses";
    }

    // XÓA

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return "redirect:/expenses";
    }
    
 // MỞ FORM EDIT

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute(
                "expense",
                service.getById(id)
        );

        model.addAttribute(
                "categories",
                categoryService.getAll()
        );

        return "expense/edit";
    }
}