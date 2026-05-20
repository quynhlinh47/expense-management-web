package com.quanlychitieu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanlychitieu.model.Category;
import com.quanlychitieu.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục

    public List<Category> getAll(){

        return categoryRepository.findAll();
    }

    // Lấy theo ID

    public Category getById(Long id){

        return categoryRepository.findById(id).orElse(null);
    }

    // Lưu

    public void save(Category category){

        categoryRepository.save(category);
    }

    // Xóa

    public boolean delete(Long id){

        try {

            categoryRepository.deleteById(id);

            return true;

        } catch (Exception e){

            return false;
        }
    }

    // Lấy theo loại

    public List<Category> getByType(String type){

        return categoryRepository.findByType(type);
    }
    public long getCategoryCount(){

        return categoryRepository.count();
    }

}