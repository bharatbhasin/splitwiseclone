package com.bbh.splitwise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bbh.splitwise.model.Expense;
import com.bbh.splitwise.model.Transaction;
import com.bbh.splitwise.model.User;

@Component
public class PercentSplitCalculator {
	private Logger log = LoggerFactory.getLogger(PercentSplitCalculator.class);

	public List<Transaction> split(Expense expense) {
		log.info("Calculating percent split");
		List<Transaction> transactionsResult = new ArrayList<Transaction>();
		Map<Long, Double> userShareMap = new HashMap<Long, Double>();
		List<Transaction> transactions = expense.getTransactions();
		Map<User, Double> shareMap = expense.getShare();
		Double expenseAmount = expense.getExpenseAmount();
		for (Transaction t : transactions) {
			if (userShareMap.containsKey(t.getReceiver().getUserId())) {
				userShareMap.put(t.getReceiver().getUserId(),
						userShareMap.get(t.getReceiver().getUserId()) + t.getAmount());
			} else {
				userShareMap.put(t.getReceiver().getUserId(), t.getAmount());
			}

			if (userShareMap.containsKey(t.getPayer().getUserId())) {
				userShareMap.put(t.getPayer().getUserId(), userShareMap.get(t.getPayer().getUserId()) - t.getAmount());
			} else {
				userShareMap.put(t.getPayer().getUserId(), -1 * t.getAmount());
			}
		}

		for (User user : expense.getUsers()) {
			Double userOwedAmount = userShareMap.get(user.getUserId());
			Double userShareFromExpense = (expenseAmount * shareMap.get(user)) / 100.0;
			Transaction resultTran = new Transaction(null, null, user, userOwedAmount + userShareFromExpense, expense);
			transactionsResult.add(resultTran);
		}
		return transactionsResult;
	}
}
