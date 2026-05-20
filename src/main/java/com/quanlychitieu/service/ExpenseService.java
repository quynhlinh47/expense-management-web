package com.quanlychitieu.service;

import com.quanlychitieu.model.Expense;
import com.quanlychitieu.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    // ================= FILTER =================

    public List<Expense> getFilteredExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId,
            String sortBy) {

        Sort sort = switch (sortBy) {

            case "oldest" ->
                    Sort.by("expenseDate").ascending();

            case "amount-desc" ->
                    Sort.by("amount").descending();

            case "amount-asc" ->
                    Sort.by("amount").ascending();

            default ->
                    Sort.by("expenseDate").descending();
        };

        return repository.findWithFilters(
                startDate,
                endDate,
                categoryId,
                sort
        );
    }

    // ================= DASHBOARD =================

    public Double getTotalExpense() {

        Double total = repository.getTotalExpense();

        return total != null ? total : 0.0;
    }

    public Double getTotalByCategory(Long categoryId) {

        Double total =
                repository.sumByCategory(categoryId);

        return total != null ? total : 0.0;
    }

    public Long getExpenseCount() {

        return repository.count();
    }

    // ================= CRUD =================

    public List<Expense> getAll() {

        return repository.findAll(
                Sort.by("expenseDate").descending()
        );
    }

    public void save(Expense expense) {

        repository.save(expense);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }

    public Expense getById(Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    public List<Expense> getRecentExpenses() {

        return repository.findTop5ByOrderByIdDesc();
    }

    // ================= CHART =================

    public List<Object[]> getMonthlyExpense() {

        return repository.getMonthlyExpense();
    }

    public List<Object[]> getMonthlyExpense(int year) {

        return repository.getMonthlyExpense(year);
    }
}