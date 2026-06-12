package com.example.flowable.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flowable.entities.DoaApprovalStep;

public interface DoaApprovalStepRepository extends JpaRepository<DoaApprovalStep, UUID> {
	Optional<DoaApprovalStep> findFirstByApprovalObjectIdAndApprovalLevel(UUID approvalObjectId, Integer approvalLevel);
}