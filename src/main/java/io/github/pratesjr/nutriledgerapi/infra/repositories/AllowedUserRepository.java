package io.github.pratesjr.nutriledgerapi.infra.repositories;

import io.github.pratesjr.nutriledgerapi.infra.entities.AllowedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AllowedUserRepository extends JpaRepository<AllowedUserEntity, Long>, JpaSpecificationExecutor<AllowedUserEntity> {
    // Only generic CRUD methods
    Optional<AllowedUserEntity> findByEmail(String email);
}
