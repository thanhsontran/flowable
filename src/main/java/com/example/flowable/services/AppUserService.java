package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.AppUser;
import com.example.flowable.repositories.AppUserRepository;

@Service
public class AppUserService extends CrudService<AppUser> {

    public AppUserService(AppUserRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(AppUser entity) {
        return entity.getId();
    }

    @Override
    protected void setId(AppUser entity, UUID id) {
        entity.setId(id);
    }
}