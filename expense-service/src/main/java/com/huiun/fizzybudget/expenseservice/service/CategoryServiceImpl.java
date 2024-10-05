package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entity.Category;
import com.huiun.fizzybudget.expenseservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
