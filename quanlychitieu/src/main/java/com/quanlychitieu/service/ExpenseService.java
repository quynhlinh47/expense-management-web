package com.quanlychitieu.service;

import com.quanlychitieu.model.Expense;
import com.quanlychitieu.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    public List<Expense> getAll() {

        return repository.findAll();
    }

    public void save(Expense expense) {

        repository.save(expense);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }
    public Double getTotalExpense() {
    	return repository.getTotalExpense();
    }
    
    public Expense getById(Long id){

        return repository.findById(id).orElse(null);
    }
    public List<Expense> getRecentExpenses(){

        return repository.findTop5ByOrderByIdDesc();
    }
    public Long getExpenseCount(){

        return repository.count();
    }
   
}