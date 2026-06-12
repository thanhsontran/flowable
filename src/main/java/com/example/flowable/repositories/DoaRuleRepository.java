package com.example.flowable.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flowable.entities.DoaRule;

public interface DoaRuleRepository extends JpaRepository<DoaRule, UUID> {
	List<DoaRule> findByCategory_NameAndActiveTrue(String categoryName);
}