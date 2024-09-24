package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entities.Category;
import com.huiun.fizzybudget.common.entities.Currency;
import com.huiun.fizzybudget.common.entities.User;
import com.huiun.fizzybudget.common.repository.UserRepository;
import com.huiun.fizzybudget.expenseservice.exception.CategoryNotFoundException;
import com.huiun.fizzybudget.expenseservice.exception.CurrencyNotFoundException;
import com.huiun.fizzybudget.expenseservice.exception.UserNotFoundException;
import com.huiun.fizzybudget.expenseservice.repository.CategoryRepository;
import com.huiun.fizzybudget.expenseservice.repository.CurrencyRepository;
import com.huiun.fizzybudget.expenseservice.repository.ExpenseRepository;
import com.huiun.fizzybudget.common.entities.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public Page<Expense> findAllByUserId(Long userId, Pageable pageable) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        return expenseRepository.findAllByUserId(user.getUserId(), pageable);
    }

    @Override
    public Page<Expense> findAllByCategoryName(String categoryName, Pageable pageable) {

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);

        return expenseRepository.findAllByCategoryId(category.getCategoryId(), pageable);
    }

    @Override
    public Page<Expense> findAllByCurrencyCode(String currencyCode, Pageable pageable) {

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findAllByCurrencyId(currency.getCurrencyId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCategoryName(Long userId, String categoryName, Pageable pageable) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);
        return expenseRepository.findAllByUserIdAndCategoryId(user.getUserId(), category.getCategoryId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCurrencyCode(Long userId, String currencyCode, Pageable pageable) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findAllByUserIdAndCurrencyId(user.getUserId(), currency.getCurrencyId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCategoryNameAndCurrencyCode(Long userId, String categoryName, String currencyCode, Pageable pageable) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findAllByUserIdAndCategoryIdAndCurrencyId(
                user.getUserId(), category.getCategoryId(), currency.getCurrencyId(),
                pageable);
    }
}
