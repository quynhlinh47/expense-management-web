package com.quanlychitieu.repository;

import com.quanlychitieu.model.Income;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("""
        SELECT i FROM Income i
        WHERE (:startDate IS NULL OR i.incomeDate >= :startDate)
          AND (:endDate IS NULL OR i.incomeDate <= :endDate)
          AND (:categoryId IS NULL OR i.category.id = :categoryId)
        """)
    List<Income> findWithFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("categoryId") Long categoryId,
            Sort sort
    );

    // Giữ lại các query cũ
    @Query("SELECT SUM(i.amount) FROM Income i")
    Double sumIncome();

    @Query("""
        SELECT MONTH(i.incomeDate), SUM(i.amount)
        FROM Income i
        GROUP BY MONTH(i.incomeDate)
        ORDER BY MONTH(i.incomeDate)
    """)
    List<Object[]> getMonthlyIncome();
}