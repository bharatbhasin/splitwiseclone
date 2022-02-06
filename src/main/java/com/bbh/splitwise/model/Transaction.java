package com.bbh.splitwise.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transaction_id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
	private User receiver;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payer_id", referencedColumnName = "user_id")
	private User payer;
	@Column(name = "amount")
	private Double amount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id", referencedColumnName = "expense_id", nullable = false, unique = true)
	private Expense expense;
}
