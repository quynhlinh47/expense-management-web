package com.quanlychitieu.controller;

import com.quanlychitieu.model.Income;
import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private CategoryService categoryService;

    // LIST + FILTER
    @GetMapping
    public String listIncome(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "newest") String sort,
            Model model) {

        LocalDate sDate = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
        LocalDate eDate = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

        model.addAttribute("incomes", 
            incomeService.getFilteredIncomes(sDate, eDate, categoryId, sort));

        // Truyền lại giá trị filter để giữ trạng thái form
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);

        model.addAttribute("categories", categoryService.getByType("INCOME"));
        model.addAttribute("pageTitle", "Thu nhập");

        return "income/index";
    }

    // ADD FORM
    @GetMapping("/add")
    public String addIncome(Model model) {
        model.addAttribute("income", new Income());
        model.addAttribute("categories", categoryService.getByType("INCOME"));
        model.addAttribute("pageTitle", "Thêm thu nhập");
        return "income/add";
    }

    // SAVE
    @PostMapping("/save")
    public String saveIncome(@ModelAttribute Income income) {
        incomeService.saveIncome(income);
        return "redirect:/incomes";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editIncome(@PathVariable Long id, Model model) {
        model.addAttribute("income", incomeService.getIncomeById(id));
        model.addAttribute("categories", categoryService.getByType("INCOME"));
        model.addAttribute("pageTitle", "Sửa thu nhập");
        return "income/edit";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return "redirect:/incomes";
    }
}