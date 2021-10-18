package com.zaincheema.auth.repository;

import java.util.Optional;

import com.zaincheema.auth.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByProviderId(String providerId);

    Boolean existsByEmail(String email);

    Optional<User> findByProviderId(String providerId);
}
