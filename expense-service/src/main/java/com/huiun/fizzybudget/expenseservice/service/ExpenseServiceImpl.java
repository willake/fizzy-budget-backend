package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.expenseservice.repository.ExpenseRepository;
import com.huiun.fizzybudget.sharedentities.Expense;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAllExpense() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> findAllExpenseByUserId(Long userId) {
        Optional<List<Expense>> foundExpenses = expenseRepository.findAllByUserId(userId);

        return foundExpenses.orElseGet(List::of);
    }
}
