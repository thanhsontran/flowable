package com.example.flowable.listeners;

import java.util.UUID;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.services.AppUserService;
import com.example.flowable.services.DoaApprovalStepService;

@Component
public class DoaTaskAssignmentListener implements TaskListener {

    private final DoaApprovalStepService doaApprovalStepService;
    private final AppUserService appUserService;

    public DoaTaskAssignmentListener(DoaApprovalStepService doaApprovalStepService, AppUserService appUserService) {
        this.doaApprovalStepService = doaApprovalStepService;
        this.appUserService = appUserService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        UUID prId = (UUID) delegateTask.getVariable("prID");
        Integer currentStep = (Integer) delegateTask.getVariable("currentStep");

        doaApprovalStepService.getNextApprovalStep(prId, currentStep)
            .ifPresent(nextStep -> assignTask(delegateTask, nextStep));
    }

    private void assignTask(DelegateTask delegateTask, DoaApprovalStep nextStep) {
        appUserService.findFirstByRoleId(nextStep.getRule().getApproverRoleId())
            .ifPresent(user -> delegateTask.setAssignee(user.getUsername()));
        delegateTask.setVariable("isFinalStep", nextStep.getFinalStep());
    }
}