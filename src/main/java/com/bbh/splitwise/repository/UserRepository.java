package com.bbh.splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbh.splitwise.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
