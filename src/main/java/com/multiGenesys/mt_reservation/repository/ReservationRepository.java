package com.multiGenesys.mt_reservation.repository;

import com.multiGenesys.mt_reservation.entity.Reservation;
import com.multiGenesys.users.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

      List<Reservation> findByUserId(Long userId);

      Page<Reservation> findByUserId(Long userId, Pageable pageable);

}
