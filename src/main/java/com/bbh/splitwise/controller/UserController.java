package com.bbh.splitwise.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import com.bbh.splitwise.model.Transaction;
import com.bbh.splitwise.model.User;
import com.bbh.splitwise.repository.TransactionRepository;
import com.bbh.splitwise.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	TransactionRepository transactionRepo;

	@RequestMapping(method = RequestMethod.POST)
	@Operation(summary = "Create a new user", description = "Create a new user.", tags = { "User" }, responses = {
			@ApiResponse(description = "Success", responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = null;
		try {
			if (user != null) {
				savedUser = userRepo.save(user);
				return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@Operation(summary = "Get all users", description = "Get all users.", tags = { "User" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<User>> getUsers() {
		try {
			List<User> allUsers = (List<User>) userRepo.findAll();
			return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<List<User>>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@Operation(summary = "Get user by id", description = "Get user by id.", tags = { "User" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		try {
			Optional<User> user = userRepo.findById(id);
			if (user.isPresent()) {
				return new ResponseEntity<User>(user.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@Operation(summary = "Update user with id", description = "Update user with id.", tags = { "User" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			if (user == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			Optional<User> optUserInDB = userRepo.findById(id);
			if (optUserInDB.isPresent()) {
				User userInDB = optUserInDB.get();
				userInDB.setFirstName(user.getFirstName());
				userInDB.setLastName(user.getLastName());
				User savedUser = userRepo.save(userInDB);
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);
			} else {
				User savedUser = userRepo.save(user);
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@Operation(summary = "Delete user with id", description = "Delete user with id.", tags = { "User" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		try {
			userRepo.deleteById(id);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}/balance")
	@Operation(summary = "Get user balance", description = "Returns all transactions for a user, ones that he ows and the ones that he will be paid", tags = {
			"User" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })

	public ResponseEntity<List<Transaction>> getBalance(@PathVariable("userId") Long id) {
		try {
			List<Transaction> payerList = transactionRepo.findByPayer(id);
			List<Transaction> receiverList = transactionRepo.findByReceiver(id);
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.addAll(payerList);
			transactions.addAll(receiverList);
			return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
