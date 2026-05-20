package com.quanlychitieu.repository;

import com.quanlychitieu.model.Expense;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    // =========================
    // FILTER
    // =========================

    @Query("""
        SELECT e FROM Expense e
        WHERE (:startDate IS NULL
               OR e.expenseDate >= :startDate)

          AND (:endDate IS NULL
               OR e.expenseDate <= :endDate)

          AND (:categoryId IS NULL
               OR e.category.id = :categoryId)
    """)
    List<Expense> findWithFilters(
            @Param("startDate") LocalDate startDate,

            @Param("endDate") LocalDate endDate,

            @Param("categoryId") Long categoryId,

            Sort sort
    );

    // =========================
    // TOTAL EXPENSE
    // =========================

    @Query("""
        SELECT COALESCE(SUM(e.amount),0)
        FROM Expense e
    """)
    Double getTotalExpense();

    // =========================
    // TOTAL BY CATEGORY
    // =========================

    @Query("""
        SELECT COALESCE(SUM(e.amount),0)
        FROM Expense e
        WHERE e.category.id = :categoryId
    """)
    Double sumByCategory(
            @Param("categoryId")
            Long categoryId
    );

    // =========================
    // COUNT
    // =========================

    long count();

    // =========================
    // RECENT EXPENSES
    // =========================

    List<Expense> findTop5ByOrderByIdDesc();

    // =========================
    // MONTHLY EXPENSE
    // =========================

    @Query(value = """
        SELECT
            MONTH(e.expense_date) AS month,

            COALESCE(SUM(e.amount),0) AS total

        FROM expenses e

        WHERE YEAR(e.expense_date)
              = YEAR(CURRENT_DATE)

        GROUP BY MONTH(e.expense_date)

        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getMonthlyExpense();

    // =========================
    // MONTHLY EXPENSE BY YEAR
    // =========================

    @Query(value = """
        SELECT
            MONTH(e.expense_date) AS month,

            COALESCE(SUM(e.amount),0) AS total

        FROM expenses e

        WHERE YEAR(e.expense_date)
              = :year

        GROUP BY MONTH(e.expense_date)

        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getMonthlyExpense(
            @Param("year") int year
    );
}