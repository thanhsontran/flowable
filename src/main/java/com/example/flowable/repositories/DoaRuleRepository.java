package com.example.flowable.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flowable.entities.DoaRule;

public interface DoaRuleRepository extends JpaRepository<DoaRule, UUID> {
}