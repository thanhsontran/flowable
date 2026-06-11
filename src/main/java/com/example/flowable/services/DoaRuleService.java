package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaRule;
import com.example.flowable.repositories.DoaRuleRepository;

@Service
public class DoaRuleService extends CrudService<DoaRule> {

    public DoaRuleService(DoaRuleRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(DoaRule entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaRule entity, UUID id) {
        entity.setId(id);
    }
}