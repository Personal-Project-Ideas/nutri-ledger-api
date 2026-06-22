package io.github.pratesjr.nutriledgerapi.infra.repositories;

import io.github.pratesjr.nutriledgerapi.infra.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, java.util.UUID>, JpaSpecificationExecutor<UserEntity> {
    // Only generic CRUD methods
}