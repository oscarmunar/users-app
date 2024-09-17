package com.app.users.dao;

import com.app.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO extends JpaRepository<UserEntity, UUID> {

    public Optional<UserEntity> findByEmail(String email);
}
