package com.quanlychitieu.controller;

import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;
import com.quanlychitieu.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        
        model.addAttribute("totalExpense", expenseService.getTotalExpense() != null ? 
                         expenseService.getTotalExpense() : 0.0);
        
        model.addAttribute("totalIncome", incomeService.getTotalIncome() != null ? 
                         incomeService.getTotalIncome() : 0.0);
        
        double balance = (incomeService.getTotalIncome() != null ? incomeService.getTotalIncome() : 0.0) 
                       - (expenseService.getTotalExpense() != null ? expenseService.getTotalExpense() : 0.0);
        
        model.addAttribute("balance", balance);
        model.addAttribute("expenseCount", expenseService.getExpenseCount());
        model.addAttribute("categoryCount", categoryService.getCategoryCount());
        model.addAttribute("recentExpenses", expenseService.getRecentExpenses());
        model.addAttribute("pageTitle", "Dashboard");

        return "dashboard/index";
    }
}