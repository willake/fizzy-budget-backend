package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.common.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCurrencyCode(String currencyCode);

    Optional<Currency> findByCurrencyName(String currencyName);
}
