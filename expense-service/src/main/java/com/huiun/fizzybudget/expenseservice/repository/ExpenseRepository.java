package com.huiun.fizzybudget.expenseservice.repository;

import com.huiun.fizzybudget.common.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e " +
            "WHERE e.id > :afterId")
    Page<Expense> findAllByIdGreaterThan(@Param("afterId")Long afterId, Pageable pageable);

    // Find all expenses by userId
    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId")
    Page<Expense> findAllByUserId(
            @Param("userId")Long userId, Pageable pageable);

    // Find all expenses by userId with pagination
    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.id > :afterId")
    Page<Expense> findAllByUserIdAndIdGreaterThan(
            @Param("userId")Long userId,
            @Param("afterId")Long afterId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.category.id = :categoryId")
    Page<Expense> findAllByCategoryId(
            @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.category.id = :categoryId " +
            "AND e.id > :afterId")
    Page<Expense> findALlByCategoryIdAndIdGreaterThan(
            @Param("categoryId")Long categoryId,
            @Param("afterId")Long afterId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.currency.id = :currencyId")
    Page<Expense> findAllByCurrencyId(
            @Param("currencyId")Long currencyId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.currency.id = :currencyId " +
            "AND e.id > :afterId")
    Page<Expense> findAllByCurrencyIdAndIdGreaterThan(
            @Param("currencyId")Long currencyId,
            @Param("afterId")Long afterId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.category.id = :categoryId")
    Page<Expense> findAllByUserIdAndCategoryId(
            @Param("userId")Long userId,
            @Param("categoryId")Long categoryId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.category.id = :categoryId " +
            "AND e.id > :afterId")
    Page<Expense> findAllByUserIdAndCategoryIdAndIdGreaterThan(
            @Param("userId")Long userId,
            @Param("categoryId")Long categoryId,
            @Param("afterId")Long afterId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.currency.id = :currencyId")
    Page<Expense> findAllByUserIdAndCurrencyId(
            @Param("userId")Long userId,
            @Param("currencyId")Long currencyId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.currency.id = :currencyId " +
            "AND e.id > :afterId")
    Page<Expense> findAllByUserIdAndCurrencyIdAndIdGreaterThan(
            @Param("userId")Long userId,
            @Param("currencyId")Long currencyId,
            @Param("afterId")Long afterId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.category.id = :categoryId " +
            "AND e.currency.id = :currencyId")
    Page<Expense> findExpensesByFilters(
            @Param("userId")Long userId,
            @Param("categoryId")Long categoryId,
            @Param("currencyId")Long currencyId, Pageable pageable);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.category.id = :categoryId " +
            "AND e.currency.id = :currencyId " +
            "AND e.id > :afterId")
    Page<Expense> findExpensesByFiltersAndIdGreaterThan(
            @Param("userId")Long userId,
            @Param("categoryId")Long categoryId,
            @Param("currencyId")Long currencyId,
            @Param("afterId")Long afterId, Pageable pageable);
}
