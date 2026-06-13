package com.example.flowable.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.flowable.entities.AppUser;
import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.entities.DoaCategory;
import com.example.flowable.entities.PurchaseRequest;
import com.example.flowable.services.AppUserService;
import com.example.flowable.services.DoaApprovalStepService;
import com.example.flowable.services.DoaCategoryService;
import com.example.flowable.services.DoaRuleService;
import com.example.flowable.services.PurchaseRequestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/pr")
@RestController
public class PRController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final DoaRuleService doaRuleService;
    private final DoaCategoryService doaCategoryService;
    private final AppUserService appUserService;
    private final PurchaseRequestService purchaseRequestService;
    private final DoaApprovalStepService doaApprovalStepService;

    public PRController(
        RuntimeService runtimeService,
        TaskService taskService,
        DoaRuleService doaRuleService,
        DoaCategoryService doaCategoryService,
        AppUserService appUserService,
        PurchaseRequestService purchaseRequestService,
        DoaApprovalStepService doaApprovalStepService
    ) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.doaRuleService = doaRuleService;
        this.doaCategoryService = doaCategoryService;
        this.appUserService = appUserService;
        this.purchaseRequestService = purchaseRequestService;
        this.doaApprovalStepService = doaApprovalStepService;
    }

    @GetMapping("/createPR")
    public String createPR() {
        DoaCategory category = doaCategoryService.findByName("Office Supplies")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DOA category not found"));
        AppUser requester = appUserService.findByUsername("son.tran")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requester not found"));

        PurchaseRequest purchaseRequest = purchaseRequestService.createPurchaseRequest(
            category,
            requester,
            new BigDecimal("150000000")
        );

        doaRuleService.createDoaApprovalSteps(purchaseRequest.getId(), category.getName(), purchaseRequest.getAmount());
        
        int currentLevel = 0;
		if (doaApprovalStepService.getNextApprovalStep(purchaseRequest.getId(), currentLevel).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No DOA approval steps found for purchase request");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("currentStep", currentLevel);
        variables.put("prID", purchaseRequest.getId());
        variables.put("isFinalStep", Boolean.FALSE);

        ProcessInstance process = runtimeService.startProcessInstanceByKey("genericDoaProcess", variables);
        String id = process.getId();
        return "Purchase Request Created - Start Flowable Process : " + id + " for PR " + purchaseRequest.getId();
    }
	
	@GetMapping("show/{prID}")
	public Object getPR(@PathVariable String prID) {
		return purchaseRequestService.requireById(UUID.fromString(prID));
	}

		
    @PostMapping("/tasks/{taskId}/complete")
    public String completeTask(@PathVariable String taskId, @RequestParam String action) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + taskId);
        }

        UUID prId = (UUID) taskService.getVariable(taskId, "prID");
        Integer currentStep = (Integer) taskService.getVariable(taskId, "currentStep");
        Boolean finalStep = (Boolean) taskService.getVariable(taskId, "isFinalStep");

        if (prId == null || currentStep == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task is missing workflow variables");
        }

        boolean isFinalStep = Boolean.TRUE.equals(finalStep);
		PurchaseRequest purchaseRequest = purchaseRequestService.requireById(prId);
		DoaApprovalStep currentApprovalStep = purchaseRequest.getCurrentApprovalStep();
		currentApprovalStep.setAction(action);
		
		purchaseRequestService.updateForApprovalAction(prId, action, isFinalStep);
		
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", action.toUpperCase());

        taskService.complete(taskId, variables);
		doaApprovalStepService.update(currentApprovalStep.getId(), currentApprovalStep);
        return "Task " + taskId + " completed with action " + action.toUpperCase() + " for PR " + prId;
    }
	
	
}
