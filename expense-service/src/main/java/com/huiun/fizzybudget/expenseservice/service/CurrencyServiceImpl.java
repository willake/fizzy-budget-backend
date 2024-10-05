package com.huiun.fizzybudget.expenseservice.service;

import com.huiun.fizzybudget.common.entity.Currency;
import com.huiun.fizzybudget.expenseservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
