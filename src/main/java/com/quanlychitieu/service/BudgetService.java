package com.quanlychitieu.service;

import com.quanlychitieu.model.Budget;
import com.quanlychitieu.repository.BudgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getAllBudgets(){

        return budgetRepository.findAll();

    }

    public void saveBudget(Budget budget){

        budgetRepository.save(budget);

    }

    public void deleteBudget(Long id){

        budgetRepository.deleteById(id);

    }

    public Budget getBudgetById(Long id){

        return budgetRepository.findById(id).orElse(null);

    }
    public Budget getById(Long id){

        return budgetRepository.findById(id).orElse(null);
    }

}