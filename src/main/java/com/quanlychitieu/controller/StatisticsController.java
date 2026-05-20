package com.quanlychitieu.controller;

import com.quanlychitieu.model.Expense;
import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;
import com.quanlychitieu.service.IncomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/statistics")
    public String statistics(Model model){

        // ======================
        // EXPENSE LIST
        // ======================

        List<Expense> expenses =
                expenseService.getAll();

        model.addAttribute(
                "expenses",
                expenses
        );

        model.addAttribute(
                "recentExpenses",
                expenseService.getRecentExpenses()
        );

        // ======================
        // DASHBOARD
        // ======================

        model.addAttribute(
                "totalExpense",
                expenseService.getTotalExpense()
        );

        model.addAttribute(
                "expenseCount",
                expenseService.getExpenseCount()
        );

        model.addAttribute(
                "categories",
                categoryService.getByType("EXPENSE")
        );

        model.addAttribute(
                "pageTitle",
                "Thống kê"
        );

        model.addAttribute(
                "totalIncome",
                incomeService.getTotalIncome()
        );

        // ======================
        // BAR + PIE CHART DATA
        // ======================

        List<String> expenseTitles =
                new ArrayList<>();

        List<Double> expenseAmounts =
                new ArrayList<>();

        List<String> categoryNames =
                new ArrayList<>();

        List<Double> categoryTotals =
                new ArrayList<>();

        for(Expense e : expenses){

            expenseTitles.add(
                    e.getTitle()
            );

            expenseAmounts.add(
                    e.getAmount()
            );
        }

        model.addAttribute(
                "expenseTitles",
                expenseTitles
        );

        model.addAttribute(
                "expenseAmounts",
                expenseAmounts
        );

        model.addAttribute(
                "categoryNames",
                categoryNames
        );
        model.addAttribute("categoryTotals", categoryTotals);
        // ======================
        // MONTHLY DATA
        // ======================

        List<Object[]> monthlyExpense =
                expenseService.getMonthlyExpense();

        List<Object[]> monthlyIncome =
                incomeService.getMonthlyIncome();

        Double[] expenseData =
                new Double[12];

        Double[] incomeData =
                new Double[12];

        for(int i = 0; i < 12; i++){

            expenseData[i] = 0.0;
            incomeData[i] = 0.0;
        }

        // EXPENSE

        for(Object[] row : monthlyExpense){

            Integer month =
                    ((Number) row[0]).intValue();

            Double total =
                    ((Number) row[1]).doubleValue();

            expenseData[month - 1] = total;
        }

        // INCOME

        for(Object[] row : monthlyIncome){

            Integer month =
                    ((Number) row[0]).intValue();

            Double total =
                    ((Number) row[1]).doubleValue();

            incomeData[month - 1] = total;
        }

        model.addAttribute(
                "monthlyExpenseData",
                expenseData
        );

        model.addAttribute(
                "monthlyIncomeData",
                incomeData
        );
        
        categoryService.getByType("EXPENSE")
        .forEach(c -> {

            categoryNames.add(
                    c.getName()
            );

            Double total =
                    expenseService.getTotalByCategory(
                            c.getId()
                    );

            if(total == null){
                total = 0.0;
            }

            categoryTotals.add(total);
        });

        return "statistics/index";
    }
}