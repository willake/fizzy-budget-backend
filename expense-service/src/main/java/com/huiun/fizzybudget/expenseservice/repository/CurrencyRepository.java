package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.expenseservice.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
