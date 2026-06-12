package com.example.flowable.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.flowable.entities.AppUser;
import com.example.flowable.entities.DoaCategory;
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

    public PurchaseRequest createPurchaseRequest(DoaCategory category, AppUser requester, BigDecimal amount) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCategory(category);
        purchaseRequest.setRequesterId(requester.getId());
        purchaseRequest.setAmount(amount);
        purchaseRequest.setCurrency("VND");
        purchaseRequest.setCurrentApprovalLevel(0);
        purchaseRequest.setStatus("PENDING");
        return create(purchaseRequest);
    }

    public PurchaseRequest requireById(UUID id) {
        return findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase request not found: " + id));
    }

    public PurchaseRequest updateForApprovalAction(UUID prId, Integer currentStep, String action, boolean finalStep) {
        PurchaseRequest purchaseRequest = requireById(prId);
        if ("REJECT".equalsIgnoreCase(action)) {
            purchaseRequest.setStatus("REJECTED");
        } else if ("APPROVE".equalsIgnoreCase(action)) {
            purchaseRequest.setCurrentApprovalLevel(currentStep);
            purchaseRequest.setStatus(finalStep ? "APPROVED" : "IN_APPROVAL");
        }
        return update(prId, purchaseRequest);
    }
}