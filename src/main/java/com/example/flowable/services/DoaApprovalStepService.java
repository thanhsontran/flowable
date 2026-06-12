package com.example.flowable.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.repositories.DoaApprovalStepRepository;

@Service
public class DoaApprovalStepService extends CrudService<DoaApprovalStep> {

    private final DoaApprovalStepRepository repository;

    public DoaApprovalStepService(DoaApprovalStepRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    protected UUID getId(DoaApprovalStep entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaApprovalStep entity, UUID id) {
        entity.setId(id);
    }

    public Optional<DoaApprovalStep> getNextApprovalStep(UUID prId, Integer currentLevel) {
        return repository.findFirstByApprovalObjectIdAndApprovalLevel(prId, currentLevel + 1);
    }
}