package com.quanlychitieu.service;

import com.quanlychitieu.model.Category;

import com.quanlychitieu.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAll() {

        return repository.findAll();
    }
    public Long getCategoryCount(){

        return repository.count();
    }
}