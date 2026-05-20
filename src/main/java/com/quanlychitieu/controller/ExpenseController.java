package com.quanlychitieu.controller;

import com.quanlychitieu.model.Expense;
import com.quanlychitieu.model.Notification;
import com.quanlychitieu.service.CategoryService;
import com.quanlychitieu.service.ExpenseService;
import com.quanlychitieu.service.NotificationService;
import com.quanlychitieu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    // HIỂN THỊ DANH SÁCH + BỘ LỌC
    @GetMapping
    public String list(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "newest") String sort,
            Model model) {

        LocalDate sDate = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
        LocalDate eDate = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

        model.addAttribute("expenses", 
            service.getFilteredExpenses(sDate, eDate, categoryId, sort));

        // Truyền lại giá trị filter để giữ trạng thái trên form
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);

        model.addAttribute("categories", categoryService.getByType("EXPENSE"));
        model.addAttribute("pageTitle", "Quản lý chi tiêu");

        return "expense/list";
    }

    // MỞ FORM THÊM
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("categories", categoryService.getByType("EXPENSE"));
        model.addAttribute("pageTitle", "Thêm khoản chi");
        return "expense/add";
    }

    // LƯU
    @PostMapping("/save")
    public String save(@ModelAttribute Expense expense, Authentication authentication) {
        service.save(expense);

        String email = authentication.getName();
        Notification notification = new Notification();
        notification.setMessage("Đã thêm khoản chi: " + expense.getTitle());
        notification.setType("expense");
        notification.setUser(userService.findByEmail(email));
        notificationService.save(notification);

        return "redirect:/expenses";
    }

    // XÓA
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/expenses";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("expense", service.getById(id));
        model.addAttribute("categories", categoryService.getByType("EXPENSE"));
        model.addAttribute("pageTitle", "Sửa khoản chi");
        return "expense/edit";
    }
}