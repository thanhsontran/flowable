package com.example.flowable.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.flowable.entities.Role;
import com.example.flowable.repositories.RoleRepository;

@Service
public class RoleService extends CrudService<Role> {

    public RoleService(RoleRepository repository) {
        super(repository);
    }

    @Override
    protected UUID getId(Role entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Role entity, UUID id) {
        entity.setId(id);
    }
}