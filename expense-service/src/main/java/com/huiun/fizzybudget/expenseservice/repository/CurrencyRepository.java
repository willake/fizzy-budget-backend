package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.sharedentities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
