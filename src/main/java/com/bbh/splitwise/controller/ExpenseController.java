package com.bbh.splitwise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbh.splitwise.model.Expense;
import com.bbh.splitwise.model.Transaction;
import com.bbh.splitwise.repository.ExpenseRepository;
import com.bbh.splitwise.service.SplitCalculatorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController(value = "/expense")
@Tag(name = "Expense", description = "Endpoints for managing expenses")
public class ExpenseController {

	@Autowired
	ExpenseRepository expenseRepo;
	@Autowired
	SplitCalculatorService splitCalculatorService;

	@RequestMapping(method = RequestMethod.POST)
	@Operation(summary = "Create a new expense", description = "Create a new expense.", tags = {
			"Expense" }, responses = { @ApiResponse(description = "Success", responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
		try {
			if (expense == null) {
				new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				expenseRepo.save(expense);
				return new ResponseEntity<Expense>(expense, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{expenseId}")
	@Operation(summary = "Get expense by expenseId", description = "Get expense by expenseId.", tags = {
			"Expense" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Expense> getExpenseById(@PathVariable("expenseId") Long id) {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{expenseId}/transaction")
	@Operation(summary = "Add transaction to an expense", description = "Add transaction to an expense .", tags = {
			"Expense" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Expense> addTransaction(@PathVariable("expenseId") Long id,
			@RequestBody Transaction transaction) {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{expenseId}/split")
	@Operation(summary = "Create a split for an expense", description = "Create a split for an expense.", tags = {
			"Expense" }, responses = { @ApiResponse(description = "Success", responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<Transaction>> split(@PathVariable("expenseId") Long id) {
		Optional<Expense> expenseForId = expenseRepo.findById(id);
		if (expenseForId.isPresent()) {
			Expense expense = expenseForId.get();
			List<Transaction> splitTrans = splitCalculatorService.split(expense);
			return new ResponseEntity<List<Transaction>>(splitTrans, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{expenseId}")
	@Operation(summary = "Delete an expense", description = "Delete an expense.", tags = { "Expense" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Expense> deleteExpense(@PathVariable("expenseId") Long id) {
		Expense expense = new Expense();
		return new ResponseEntity<Expense>(expense, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{expenseId}/transaction")
	@Operation(summary = "Delete a transaction from an expense with expenseId", description = "Delete a transaction from an expense with expenseId.", tags = {
			"Expense" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Expense.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Expense> deleteTransaction(@PathVariable("expenseId") Long id,
			@PathVariable("tid") Long tid) {
		Expense expense = new Expense();
		return new ResponseEntity<Expense>(expense, HttpStatus.NO_CONTENT);
	}

}
