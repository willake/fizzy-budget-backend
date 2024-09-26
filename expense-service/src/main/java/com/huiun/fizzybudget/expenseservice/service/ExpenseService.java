package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entity.Expense;
import com.huiun.fizzybudget.expenseservice.dto.ExpenseConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExpenseService {

    ExpenseConnection findAll(Long afterId, Pageable pageable);

    ExpenseConnection findAllByFilters(ExpenseFilter filter, Long afterId, Pageable pageable);
}
