package com.example.flowable.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.DoaCategory;
import com.example.flowable.repositories.DoaCategoryRepository;

@Service
public class DoaCategoryService extends CrudService<DoaCategory> {

    private final DoaCategoryRepository repository;

    public DoaCategoryService(DoaCategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    protected UUID getId(DoaCategory entity) {
        return entity.getId();
    }

    @Override
    protected void setId(DoaCategory entity, UUID id) {
        entity.setId(id);
    }

    public Optional<DoaCategory> findByName(String name) {
        return repository.findByName(name);
    }
}