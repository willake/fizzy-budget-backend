package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entity.Expense;
import com.huiun.fizzybudget.expenseservice.dto.ExpenseConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    ExpenseConnection findAll(Pageable pageable);

    ExpenseConnection findAllAfter(Long afterId, Pageable pageable);

    Page<Expense> findAllByUserId(Long userId, Pageable pageable);

    Page<Expense> findAllByCategoryName(String categoryName, Pageable pageable);

    Page<Expense> findAllByCurrencyCode(String currencyCode, Pageable pageable);

    Page<Expense> findAllByUserIdAndCategoryName(Long userId, String categoryName, Pageable pageable);

    Page<Expense> findAllByUserIdAndCurrencyCode(Long userId, String currencyCode, Pageable pageable);

    Page<Expense> findAllByUserIdAndCategoryNameAndCurrencyCode(Long userId, String categoryName, String currencyCode, Pageable pageable);
}
