package com.example.flowable.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	@Column(name = "approver_username", length = 50, nullable = false)
	private String approverUserName;
	
	@Column(name = "step_action", length = 20)
	private String action;

}