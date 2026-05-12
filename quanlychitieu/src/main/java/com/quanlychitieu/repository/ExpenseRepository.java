package com.quanlychitieu.repository;

import com.quanlychitieu.model.Expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

	@Query("SELECT SUM(e.amount) FROM Expense e")
	Double getTotalExpense();
	
	long count();
	
	List<Expense> findTop5ByOrderByIdDesc();
}