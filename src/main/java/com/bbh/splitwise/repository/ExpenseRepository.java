package com.bbh.splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbh.splitwise.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
