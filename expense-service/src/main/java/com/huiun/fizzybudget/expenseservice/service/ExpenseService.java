package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.expenseservice.entity.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpense();

    List<Expense> findAllExpenseByUserId(Long userId);
}
