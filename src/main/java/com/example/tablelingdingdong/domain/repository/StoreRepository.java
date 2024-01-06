package com.example.tablelingdingdong.domain.repository;

import com.example.tablelingdingdong.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
