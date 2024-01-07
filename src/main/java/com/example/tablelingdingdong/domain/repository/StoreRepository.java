package com.example.tablelingdingdong.domain.repository;

import com.example.tablelingdingdong.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Optional<Store> findByIdAndManagerId(Long storeId, Long managerId);
}
