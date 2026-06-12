package com.example.flowable.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.entities.DoaRule;
import com.example.flowable.repositories.DoaRuleRepository;

@Service
public class DoaRuleService extends CrudService<DoaRule> {

    private final DoaRuleRepository repository;
    private final DoaApprovalStepService doaApprovalStepService;

    public DoaRuleService(DoaRuleRepository repository, DoaApprovalStepService doaApprovalStepService) {
        super(repository);
        this.repository = repository;
        this.doaApprovalStepService = doaApprovalStepService;
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
        for (DoaRule matchedRule : matchedRules) {
            DoaApprovalStep step = new DoaApprovalStep();
            step.setApprovalObjectId(prId);
            step.setRule(matchedRule);
            step.setApprovalLevel(approvalLevel++);
            step.setFinalStep(Boolean.FALSE);
            createdSteps.add(doaApprovalStepService.create(step));
        }

        if (!createdSteps.isEmpty()) {
            DoaApprovalStep lastStep = createdSteps.get(createdSteps.size() - 1);
            lastStep.setFinalStep(Boolean.TRUE);
            createdSteps.set(createdSteps.size() - 1,
                doaApprovalStepService.update(lastStep.getId(), lastStep));
        }

        return createdSteps;
    }

    private boolean matchesAmount(DoaRule rule, BigDecimal amount) {
        BigDecimal minAmount = rule.getMinAmount() == null ? BigDecimal.ZERO : rule.getMinAmount();
        BigDecimal maxAmount = rule.getMaxAmount();

        boolean minMatches = minAmount.compareTo(amount) <= 0;
        boolean maxMatches = maxAmount == null || maxAmount.compareTo(amount) <= 0 || maxAmount.compareTo(amount) > 0;

        if (maxAmount == null) {
            return minMatches;
        }

        return (maxAmount.compareTo(amount) <= 0 || maxAmount.compareTo(amount) > 0) && minMatches;
    }
}