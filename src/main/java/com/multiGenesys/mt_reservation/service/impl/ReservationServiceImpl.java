package com.multiGenesys.mt_reservation.service.impl;

import com.multiGenesys.mt_reservation.repository.ReservationRepository;
import com.multiGenesys.mt_reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

      private final ReservationRepository reservationRepository;
}
