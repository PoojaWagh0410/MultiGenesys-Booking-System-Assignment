package com.multiGenesys.mt_reservation.controller;

import com.multiGenesys.mt_reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

      private final ReservationService reservationService;
}
