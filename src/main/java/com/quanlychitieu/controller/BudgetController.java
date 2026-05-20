package com.quanlychitieu.controller;

import com.quanlychitieu.model.Budget;
import com.quanlychitieu.service.BudgetService;
import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    // LIST
    @GetMapping
    public String listBudget(Model model){

        for(Budget b : budgetService.getAllBudgets()){

            Double spent =
                    expenseService.getTotalByCategory(
                            b.getCategory().getId()
                    );

            b.getCategory().setTotalSpent(
                    spent != null ? spent : 0
            );
        }

        model.addAttribute(
                "budgets",
                budgetService.getAllBudgets()
        );

        model.addAttribute(
                "pageTitle",
                "Ngân sách"
        );

        return "budget/index";
    }

    // ADD
    @GetMapping("/add")
    public String addBudget(Model model){

        model.addAttribute(
                "budget",
                new Budget()
        );

        model.addAttribute(
                "categories",
                categoryService.getByType("EXPENSE")
        );

        model.addAttribute(
                "pageTitle",
                "Thêm ngân sách"
        );

        return "budget/add";
    }

    // SAVE
    @PostMapping("/save")
    public String saveBudget(
            @ModelAttribute Budget budget
    ){

        budgetService.saveBudget(budget);

        return "redirect:/budgets";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBudget(
            @PathVariable Long id
    ){

        budgetService.deleteBudget(id);

        return "redirect:/budgets";
    }
    
 // EDIT
    @GetMapping("/edit/{id}")
    public String editBudget(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute(
                "budget",
                budgetService.getById(id)
        );

        model.addAttribute(
                "categories",
                categoryService.getByType("EXPENSE")
        );

        model.addAttribute(
                "pageTitle",
                "Sửa ngân sách"
        );

        return "budget/edit";
    }
}