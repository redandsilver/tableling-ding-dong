package com.example.tablelingdingdong.domain.repository;

import com.example.tablelingdingdong.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean findByReservationTime(LocalDateTime time);
}
