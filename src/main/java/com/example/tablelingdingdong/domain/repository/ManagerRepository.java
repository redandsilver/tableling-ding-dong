package com.example.tablelingdingdong.domain.repository;

import com.example.tablelingdingdong.domain.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Optional<Manager> findByEmail(String email);
    Optional<Manager> findByEmailAndVerifyIsTrue(String email);
    Optional<Manager> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);

    Optional<Manager> findByIdAndEmail(Long id, String email);
}
