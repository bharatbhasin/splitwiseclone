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
public class EqualSplitCalculator {
	private Logger log = LoggerFactory.getLogger(EqualSplitCalculator.class);

	public List<Transaction> split(Expense expense) {
		log.info("Calculating equal split");
		List<Transaction> transactionsResult = new ArrayList<Transaction>();
		Map<Long, Double> userShareMap = new HashMap<Long, Double>();
		Double totalAmount = expense.getExpenseAmount();
		List<User> expenseUserList = expense.getUsers();
		Double perUserShare = totalAmount / (double) expenseUserList.size();
		List<Transaction> alreadyPaidShare = expense.getTransactions();
		for (Transaction tran : alreadyPaidShare) {
			User payer = tran.getPayer();
			if (userShareMap.containsKey(payer.getUserId())) {
				userShareMap.put(payer.getUserId(), userShareMap.get(payer.getUserId()) + tran.getAmount());
			} else {
				userShareMap.put(payer.getUserId(), tran.getAmount());
			}
		}
		for (User user : expenseUserList) {
			if (userShareMap.containsKey(user.getUserId())) {
				Double alreadyPaidAmount = userShareMap.get(user.getUserId());
				Double owedAmount = perUserShare - alreadyPaidAmount;
				Transaction userTran = new Transaction(null, null, user, owedAmount, expense);
				transactionsResult.add(userTran);
			} else {
				Transaction userTran = new Transaction(null, null, user, perUserShare, expense);
				transactionsResult.add(userTran);
			}
		}
		return transactionsResult;
	}
}