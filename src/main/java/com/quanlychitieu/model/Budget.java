package com.quanlychitieu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budgets")

@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Double limitAmount;

    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
    
    
}