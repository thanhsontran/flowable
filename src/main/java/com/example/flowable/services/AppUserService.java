package com.example.flowable.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.AppUser;
import com.example.flowable.repositories.AppUserRepository;

@Service
public class AppUserService extends CrudService<AppUser> {

    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    protected UUID getId(AppUser entity) {
        return entity.getId();
    }

    @Override
    protected void setId(AppUser entity, UUID id) {
        entity.setId(id);
    }

    public Optional<AppUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<AppUser> findFirstByRoleId(UUID roleId) {
        return repository.findFirstByRole_Id(roleId);
    }

    public Optional<String> getUsernameById(UUID id) {
        return repository.findById(id).map(AppUser::getUsername);
    }
}