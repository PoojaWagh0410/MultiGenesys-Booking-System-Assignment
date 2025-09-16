package com.multiGenesys.mt_reservation.controller;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mt_reservation.dao.request.ReservationRequestDto;
import com.multiGenesys.mt_reservation.dao.response.ReservationResponseDto;
import com.multiGenesys.mt_reservation.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

      private final ReservationService reservationService;

      @PostMapping
      ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto dto) {
            return reservationService.createReservation(dto);
      }

      @GetMapping
      ResponseEntity<?> getAllReservations(
                 @RequestParam(required = false) String status,
                 @RequestParam(required = false) Double minPrice,
                 @RequestParam(required = false) Double maxPrice,
                 @RequestParam(defaultValue = "0") int page,
                 @RequestParam(defaultValue = "10") int size
      ) {
            return reservationService.getAllReservations(status, minPrice, maxPrice, page, size);
      }

      @GetMapping("{id}")
      ResponseEntity<?> getReservationById(@PathVariable Long id) {
            return reservationService.getReservationById(id);
      }

      @PutMapping("/{id}")
      public ResponseEntity<ApiResponse<ReservationResponseDto>> updateReservation(
                 @PathVariable Long id,
                 @RequestBody ReservationRequestDto dto) {
            return reservationService.updateReservation(id, dto);
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<ApiResponse<String>> deleteReservation(@PathVariable Long id) {
            return reservationService.deleteReservation(id);
      }


}
