package com.multiGenesys.mt_reservation.service;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mt_reservation.dao.request.ReservationRequestDto;
import com.multiGenesys.mt_reservation.dao.response.ReservationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {

      ResponseEntity<ApiResponse<ReservationResponseDto>> createReservation(ReservationRequestDto dto);

      ResponseEntity<ApiResponse<List<ReservationResponseDto>>> getAllReservations(
                 String status,
                 Double minPrice,
                 Double maxPrice,
                 int page,
                 int size
      );

      ResponseEntity<ApiResponse<ReservationResponseDto>> getReservationById(Long id);

      ResponseEntity<ApiResponse<ReservationResponseDto>> updateReservation(Long id,  ReservationRequestDto dto);

      ResponseEntity<ApiResponse<String>> deleteReservation(Long id);


}
