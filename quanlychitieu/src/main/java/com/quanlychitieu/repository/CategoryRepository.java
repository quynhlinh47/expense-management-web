package com.quanlychitieu.repository;

import com.quanlychitieu.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

	long count();
}