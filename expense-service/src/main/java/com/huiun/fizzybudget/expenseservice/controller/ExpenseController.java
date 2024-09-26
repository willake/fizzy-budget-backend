package com.huiun.fizzybudget.expenseservice.controller;

import com.huiun.fizzybudget.expenseservice.dto.ExpenseConnection;
import com.huiun.fizzybudget.expenseservice.service.ExpenseService;
import com.huiun.fizzybudget.expenseservice.utility.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Base64;
import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @QueryMapping
    public ExpenseConnection getAllExpenses(@Argument Integer first, @Argument String after) {
        Pageable pageable;

        // Handle forward pagination (first and after)

        if (first == null) throw new IllegalArgumentException("Invalid pagination arguments: 'first' is require");

        if (after != null) {
            Long decodedCursor = PaginationUtil.decodeCursor(after);
            pageable = PageRequest.of(0, first, Sort.by("id").ascending());
            return expenseService.findAll(decodedCursor, pageable);
        }
        else {
            pageable = PageRequest.of(0, first, Sort.by("id").ascending());
            return expenseService.findAll(null, pageable);
        }
    }
}
