package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entity.Category;
import com.huiun.fizzybudget.common.entity.Currency;
import com.huiun.fizzybudget.common.entity.User;
import com.huiun.fizzybudget.common.repository.UserRepository;
import com.huiun.fizzybudget.expenseservice.dto.ExpenseConnection;
import com.huiun.fizzybudget.expenseservice.dto.ExpenseEdge;
import com.huiun.fizzybudget.expenseservice.dto.PageInfo;
import com.huiun.fizzybudget.expenseservice.exception.CategoryNotFoundException;
import com.huiun.fizzybudget.expenseservice.exception.CurrencyNotFoundException;
import com.huiun.fizzybudget.expenseservice.exception.UserNotFoundException;
import com.huiun.fizzybudget.expenseservice.repository.CategoryRepository;
import com.huiun.fizzybudget.expenseservice.repository.CurrencyRepository;
import com.huiun.fizzybudget.expenseservice.repository.ExpenseRepository;
import com.huiun.fizzybudget.common.entity.Expense;
import com.huiun.fizzybudget.expenseservice.utility.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ExpenseConnection findAll(Pageable pageable) {
        // Get all expenses without considering an afterId (starting from the beginning)
        Page<Expense> expensePage = expenseRepository.findAll(pageable);

        // Map the list of expenses to edges with encoded cursors
        List<ExpenseEdge> edges = expensePage.getContent().stream()
                .map(expense -> new ExpenseEdge(expense, PaginationUtil.encodeCursor(expense.getId())))
                .toList();

        // Build the PageInfo object to check if there are more pages
        PageInfo pageInfo = new PageInfo();
        pageInfo.setHasNextPage(expensePage.hasNext());

        return new ExpenseConnection(edges, pageInfo);
    }

    @Override
    public ExpenseConnection findAllAfter(Long afterId, Pageable pageable) {
        // Get all expenses without considering an afterId (starting from the beginning)
        Page<Expense> expensePage = expenseRepository.findAllByIdGreaterThan(afterId, pageable);

        // Map the list of expenses to edges with encoded cursors
        List<ExpenseEdge> edges = expensePage.getContent().stream()
                .map(expense -> new ExpenseEdge(expense, PaginationUtil.encodeCursor(expense.getId())))
                .toList();

        // Build the PageInfo object to check if there are more pages
        PageInfo pageInfo = new PageInfo();
        pageInfo.setHasNextPage(expensePage.hasNext());

        return new ExpenseConnection(edges, pageInfo);
    }

    @Override
    public Page<Expense> findAllByUserId(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return expenseRepository.findAllByUserId(user.getId(), pageable);
    }

    @Override
    public Page<Expense> findAllByCategoryName(String categoryName, Pageable pageable) {

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);

        return expenseRepository.findAllByCategoryId(category.getId(), pageable);
    }

    @Override
    public Page<Expense> findAllByCurrencyCode(String currencyCode, Pageable pageable) {

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findAllByCurrencyId(currency.getId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCategoryName(Long userId, String categoryName, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);
        return expenseRepository.findAllByUserIdAndCategoryId(user.getId(), category.getId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCurrencyCode(Long userId, String currencyCode, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findAllByUserIdAndCurrencyId(user.getId(), currency.getId(), pageable);
    }

    @Override
    public Page<Expense> findAllByUserIdAndCategoryNameAndCurrencyCode(Long userId, String categoryName, String currencyCode, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);

        Currency currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(CurrencyNotFoundException::new);

        return expenseRepository.findExpensesByFilters(
                user.getId(), category.getId(), currency.getId(),
                pageable);
    }
}
