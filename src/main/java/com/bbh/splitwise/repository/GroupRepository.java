package com.bbh.splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbh.splitwise.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
