package com.cavassoni.vettoripay.domain.mysql.repository;

import com.cavassoni.vettoripay.domain.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);
}
