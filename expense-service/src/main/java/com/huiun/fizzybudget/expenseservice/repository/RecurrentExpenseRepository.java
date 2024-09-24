package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.common.entities.Expense;
import com.huiun.fizzybudget.common.entities.RecurrentExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurrentExpenseRepository extends JpaRepository<RecurrentExpense, Long> {
    Page<Expense> findAllByUserId(Long userId, Pageable pageable);

    Page<Expense> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Expense> findAllByCurrencyId(Long currencyId, Pageable pageable);

    Page<Expense> findAllByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    Page<Expense> findAllByUserIdAndCurrencyId(Long userId, Long currencyId, Pageable pageable);

    Page<Expense> findAllByUserIdAndCategoryIdAndCurrencyId(Long userId, Long categoryId, Long currencyId, Pageable pageable);
}
