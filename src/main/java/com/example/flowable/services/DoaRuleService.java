package com.example.flowable.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.entities.AppUser;
import com.example.flowable.entities.DoaRule;
import com.example.flowable.repositories.DoaRuleRepository;

@Service
public class DoaRuleService extends CrudService<DoaRule> {

    private final DoaRuleRepository repository;
    private final DoaApprovalStepService doaApprovalStepService;
	private final AppUserService appUserService;

    public DoaRuleService(DoaRuleRepository repository, DoaApprovalStepService doaApprovalStepService, AppUserService appUserService) {
        super(repository);
        this.repository = repository;
        this.doaApprovalStepService = doaApprovalStepService;
		this.appUserService = appUserService;
    }

    @Override
    protected UUID getId(DoaRule entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaRule entity, UUID id) {
        entity.setId(id);
    }

    public List<DoaApprovalStep> createDoaApprovalSteps(UUID prId, String ruleName, BigDecimal amount) {
        List<DoaRule> matchedRules = repository.findByCategory_NameAndActiveTrue(ruleName).stream()
            .filter(rule -> matchesAmount(rule, amount))
            .sorted(Comparator.comparing(DoaRule::getMaxAmount, Comparator.nullsLast(BigDecimal::compareTo)))
            .toList();

        List<DoaApprovalStep> createdSteps = new ArrayList<>();
        int approvalLevel = 1;
        for (int i = 0; i < matchedRules.size(); i++) {
			DoaRule matchedRule = matchedRules.get(i);
            DoaApprovalStep step = new DoaApprovalStep();
            step.setApprovalObjectId(prId);
            step.setRule(matchedRule);
            step.setApprovalLevel(approvalLevel++);
			step.setFinalStep(Boolean.FALSE);
			
			if(i == matchedRules.size()-1) {
				step.setFinalStep(Boolean.TRUE);
			}

			String approverUserName = appUserService.findFirstByRoleId(matchedRule.getApproverRoleId())
				.map(AppUser::getUsername)
				.orElseThrow(() -> new RuntimeException(
					"No user found for approver role: " + matchedRule.getApproverRoleId()));
			step.setApproverUserName(approverUserName);
            
            createdSteps.add(doaApprovalStepService.create(step));
        }

        return createdSteps;
    }

    private boolean matchesAmount(DoaRule rule, BigDecimal amount) {
        BigDecimal minAmount = rule.getMinAmount() == null ? BigDecimal.ZERO : rule.getMinAmount();
        return minAmount.compareTo(amount) <= 0;
    }
}