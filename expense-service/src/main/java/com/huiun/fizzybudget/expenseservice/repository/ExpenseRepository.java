package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.expenseservice.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<List<Expense>> findAllByUserId(Long userId);
}
