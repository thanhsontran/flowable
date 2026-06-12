package com.example.flowable.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flowable.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
	Optional<AppUser> findByUsername(String username);

	Optional<AppUser> findFirstByRole_Id(UUID roleId);
}