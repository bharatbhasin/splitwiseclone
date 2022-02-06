package com.bbh.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbh.splitwise.model.Expense;
import com.bbh.splitwise.model.Transaction;

@Service
public class SplitCalculatorService {

	@Autowired
	private PercentSplitCalculator percentSplitCalculator;
	@Autowired
	private ExactSplitCalculator exactSplitCalculator;
	@Autowired
	private EqualSplitCalculator equalSplitCalculator;
	private Logger log = LoggerFactory.getLogger(SplitCalculatorService.class);

	public List<Transaction> split(Expense expense) {
		log.info("Calculating equal split");
		List<Transaction> transactionsResult = new ArrayList<Transaction>();
		switch (expense.getSplitType()) {
		case EQUAL:
			return equalSplitCalculator.split(expense);
		case EXACT:
			return exactSplitCalculator.split(expense);
		case PERCENT:
			return percentSplitCalculator.split(expense);
		}
		return transactionsResult;
	}
}
