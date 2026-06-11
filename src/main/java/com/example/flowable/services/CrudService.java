package com.example.flowable.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudService<T> {

    private final JpaRepository<T, UUID> repository;

    protected CrudService(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    public T create(T entity) {
        assignIdIfMissing(entity);
        return repository.save(entity);
    }

    public T update(UUID id, T entity) {
        setId(entity, id);
        return repository.save(entity);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    protected abstract UUID getId(T entity);

    protected abstract void setId(T entity, UUID id);

    private void assignIdIfMissing(T entity) {
        if (getId(entity) == null) {
            setId(entity, UUID.randomUUID());
        }
    }
}