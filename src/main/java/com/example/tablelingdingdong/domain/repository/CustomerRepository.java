package com.example.tablelingdingdong.domain.repository;

import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailAndVerifyIsTrue(String email);
    Optional<Customer> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);

    Optional<Customer> findByIdAndEmail(Long id, String email);
}
