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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public ExpenseConnection findAll(Long afterId, Pageable pageable) {
        // Get all expenses without considering an afterId (starting from the beginning)
        Page<Expense> expensePage;

        if(afterId != null) {
            expensePage = expenseRepository.findAllByIdGreaterThan(afterId, pageable);
        }
        else {
            expensePage = expenseRepository.findAll(pageable);
        }

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
    public ExpenseConnection findAllByFilters(ExpenseFilter filter, Long afterId, Pageable pageable) {
        User user = null;
        Category category = null;
        Currency currency = null;

        if(filter.getUserId() != null) {
            user = userRepository.findById(filter.getUserId())
                    .orElseThrow(UserNotFoundException::new);
        }

        if(filter.getCategoryName() != null) {
            category = categoryRepository.findByCategoryName(filter.getCategoryName())
                    .orElseThrow(CategoryNotFoundException::new);
        }

        if(filter.getCurrencyCode() != null) {
            currency = currencyRepository.findByCurrencyCode(filter.getCurrencyCode())
                    .orElseThrow(CurrencyNotFoundException::new);
        }

        // Get all expenses without considering an afterId (starting from the beginning)
        Page<Expense> expensePage = expenseRepository.findAllByFilters(
                user != null ? user.getId() : null,
                category != null ? category.getId() : null,
                currency != null ? currency.getId() : null,
                afterId,
                pageable);

        // Map the list of expenses to edges with encoded cursors
        List<ExpenseEdge> edges = expensePage.getContent().stream()
                .map(expense -> new ExpenseEdge(expense, PaginationUtil.encodeCursor(expense.getId())))
                .toList();

        // Build the PageInfo object to check if there are more pages
        PageInfo pageInfo = new PageInfo();
        pageInfo.setHasNextPage(expensePage.hasNext());

        return new ExpenseConnection(edges, pageInfo);
    }
}
