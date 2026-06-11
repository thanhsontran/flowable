package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaApprovalStep;
import com.example.flowable.repositories.DoaApprovalStepRepository;

@Service
public class DoaApprovalStepService extends CrudService<DoaApprovalStep> {

    public DoaApprovalStepService(DoaApprovalStepRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(DoaApprovalStep entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaApprovalStep entity, UUID id) {
        entity.setId(id);
    }
}