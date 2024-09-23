package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.expenseservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
