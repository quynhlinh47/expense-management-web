package com.quanlychitieu.controller;

import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute(
                "totalExpense",
                expenseService.getTotalExpense()
        );

        model.addAttribute(
                "expenseCount",
                expenseService.getExpenseCount()
        );

        model.addAttribute(
                "categoryCount",
                categoryService.getCategoryCount()
        );

        model.addAttribute(
                "recentExpenses",
                expenseService.getRecentExpenses()
        );

        return "dashboard/index";
    }
}