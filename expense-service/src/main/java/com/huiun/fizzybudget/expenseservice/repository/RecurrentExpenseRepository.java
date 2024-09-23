package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.sharedentities.RecurrentExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurrentExpenseRepository extends JpaRepository<RecurrentExpense, Long> {
}
