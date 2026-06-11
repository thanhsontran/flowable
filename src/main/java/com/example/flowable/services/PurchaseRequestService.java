package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.PurchaseRequest;
import com.example.flowable.repositories.PurchaseRequestRepository;

@Service
public class PurchaseRequestService extends CrudService<PurchaseRequest> {

    public PurchaseRequestService(PurchaseRequestRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(PurchaseRequest entity) {
        return entity.getId();
    }

    @Override
    protected void setId(PurchaseRequest entity, UUID id) {
        entity.setId(id);
    }
}