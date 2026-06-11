package com.example.flowable.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doa_approval_steps")
public class DoaApprovalStep {

    @Id
    private UUID id;

    @Column(name = "approval_object_id", nullable = false)
    private UUID approvalObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private DoaRule rule;

    @Column(name = "approval_level", nullable = false)
    private Integer approvalLevel;

    @Column(name = "is_final_step")
    private Boolean finalStep;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getApprovalObjectId() {
        return approvalObjectId;
    }

    public void setApprovalObjectId(UUID approvalObjectId) {
        this.approvalObjectId = approvalObjectId;
    }

    public DoaRule getRule() {
        return rule;
    }

    public void setRule(DoaRule rule) {
        this.rule = rule;
    }

    public Integer getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public Boolean getFinalStep() {
        return finalStep;
    }

    public void setFinalStep(Boolean finalStep) {
        this.finalStep = finalStep;
    }

}