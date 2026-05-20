package com.quanlychitieu.service;

import com.quanlychitieu.model.Income;
import com.quanlychitieu.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    // === PHƯƠNG THỨC MỚI: HỖ TRỢ LỌC ===
    public List<Income> getFilteredIncomes(
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId,
            String sortBy) {

        Sort sort = switch (sortBy) {
            case "oldest" -> Sort.by("incomeDate").ascending();
            case "amount-desc" -> Sort.by("amount").descending();
            case "amount-asc" -> Sort.by("amount").ascending();
            default -> Sort.by("incomeDate").descending(); // newest
        };

        return incomeRepository.findWithFilters(startDate, endDate, categoryId, sort);
    }

    // Các phương thức cũ
    public List<Income> getAllIncome() {
        return incomeRepository.findAll(Sort.by("incomeDate").descending());
    }

    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public Double getTotalIncome() {
        return incomeRepository.sumIncome();
    }

    public List<Object[]> getMonthlyIncome() {
        return incomeRepository.getMonthlyIncome();
    }
}