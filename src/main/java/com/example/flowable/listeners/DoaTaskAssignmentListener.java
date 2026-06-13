package com.example.flowable.listeners;

import java.util.UUID;

import com.example.flowable.services.PurchaseRequestService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.services.AppUserService;
import com.example.flowable.services.DoaApprovalStepService;
@Slf4j
@Component
public class DoaTaskAssignmentListener implements TaskListener {

    private final DoaApprovalStepService doaApprovalStepService;
	private final PurchaseRequestService purchaseRequestService;

    public DoaTaskAssignmentListener(DoaApprovalStepService doaApprovalStepService, PurchaseRequestService purchaseRequestService) {
        this.doaApprovalStepService = doaApprovalStepService;
		this.purchaseRequestService = purchaseRequestService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        UUID prId = (UUID) delegateTask.getVariable("prID");
        Integer currentStep = (Integer) delegateTask.getVariable("currentStep");
		log.info("Flowable - User task created: " + delegateTask.getId());
        doaApprovalStepService.getNextApprovalStep(prId, currentStep)
            .ifPresent(nextStep -> assignTask(delegateTask, nextStep, prId));
    }

    private void assignTask(DelegateTask delegateTask, DoaApprovalStep nextStep,  UUID prId) {
		purchaseRequestService.updateApprovalStep(prId, nextStep);
		delegateTask.setAssignee(nextStep.getApproverUserName());
        delegateTask.setVariable("isFinalStep", nextStep.getFinalStep());
		delegateTask.setVariable("currentStep", nextStep.getApprovalLevel());
		delegateTask.setVariable("approver", nextStep.getApproverUserName());
    }
}