package com.bbh.splitwise.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private Long group_id;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "group")
	private List<User> users;
	@OneToMany
	private Set<Expense> expenses;
}
