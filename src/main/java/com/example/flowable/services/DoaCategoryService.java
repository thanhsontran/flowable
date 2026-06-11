package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaCategory;
import com.example.flowable.repositories.DoaCategoryRepository;

@Service
public class DoaCategoryService extends CrudService<DoaCategory> {

    public DoaCategoryService(DoaCategoryRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(DoaCategory entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaCategory entity, UUID id) {
        entity.setId(id);
    }
}