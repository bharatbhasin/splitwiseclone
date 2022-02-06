package com.bbh.splitwise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "expense_id")
	private Long expenseId;
	private String description;
	private Double expenseAmount;
	@OneToMany(mappedBy = "expense")
	private List<Transaction> transactions;
	@OneToMany(mappedBy = "userId")
	private List<User> users;
	private SplitType splitType;
	@ElementCollection
	private Map<User, Double> share;

	public void addTransaction(Transaction transaction) {
		if (transactions == null) {
			this.transactions = new ArrayList<Transaction>();
		}
		this.transactions.add(transaction);
	}
}
