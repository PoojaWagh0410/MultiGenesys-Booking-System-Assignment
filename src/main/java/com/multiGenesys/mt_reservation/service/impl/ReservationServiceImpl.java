package com.multiGenesys.mt_reservation.service.impl;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mt_reservation.dao.request.ReservationRequestDto;
import com.multiGenesys.mt_reservation.dao.response.ReservationResponseDto;
import com.multiGenesys.mt_reservation.entity.Reservation;
import com.multiGenesys.mt_reservation.repository.ReservationRepository;
import com.multiGenesys.mt_reservation.service.ReservationService;
import com.multiGenesys.mt_resources.entity.Resources;
import com.multiGenesys.mt_resources.repository.ResourceRepository;
import com.multiGenesys.security.impl.UserDetailsImpl;
import com.multiGenesys.users.entity.Users;
import com.multiGenesys.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

      private final ReservationRepository reservationRepository;
      private final ResourceRepository resourceRepository;
      private final UserRepository userRepository;

      @Override
      public ResponseEntity<ApiResponse<ReservationResponseDto>> createReservation(ReservationRequestDto dto) {
            ApiResponse<ReservationResponseDto> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
                  response.responseMethod(401, "Unauthorized", null);
                  return ResponseEntity.status(401).body(response);
            }

            Users currentUser = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId())
                       .orElseThrow(() -> new RuntimeException("User not found"));

            Optional<Resources> resourceOpt = resourceRepository.findById(dto.getResourceId());
            if (resourceOpt.isEmpty()) {
                  response.responseMethod(404, "Resource not found", null);
                  return ResponseEntity.status(404).body(response);
            }

            Reservation reservation = new Reservation();
            reservation.setStatus(dto.getStatus());
            reservation.setPrice(dto.getPrice());
            reservation.setStartTime(dto.getStartTime());
            reservation.setEndTime(dto.getEndTime());
            reservation.setUser(currentUser);
            reservation.setResource(resourceOpt.get());

            Reservation savedReservation = reservationRepository.save(reservation);

            ReservationResponseDto responseDto = new ReservationResponseDto();
            responseDto.setId(savedReservation.getId());
            responseDto.setStatus(savedReservation.getStatus());
            responseDto.setPrice(savedReservation.getPrice());
            responseDto.setStartTime(savedReservation.getStartTime());
            responseDto.setEndTime(savedReservation.getEndTime());

            if (savedReservation.getCreatedDate() != null)
                  responseDto.setCreatedAt(savedReservation.getCreatedDate().atZone(java.time.ZoneId.systemDefault()).toInstant());
            if (savedReservation.getLastModifiedDate() != null)
                  responseDto.setUpdatedAt(savedReservation.getLastModifiedDate().atZone(java.time.ZoneId.systemDefault()).toInstant());

            responseDto.setUserId(savedReservation.getUser().getId());
            responseDto.setUsername(savedReservation.getUser().getUsername());
            responseDto.setResourceId(savedReservation.getResource().getId());
            responseDto.setResourceName(savedReservation.getResource().getName());

            response.responseMethod(201, "Reservation created successfully", responseDto);
            return ResponseEntity.status(201).body(response);
      }

      @Override
      public ResponseEntity<ApiResponse<List<ReservationResponseDto>>> getAllReservations(
                 String status, Double minPrice, Double maxPrice, int page, int size) {

            ApiResponse<List<ReservationResponseDto>> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
                  response.responseMethod(401, "Unauthorized", null);
                  return ResponseEntity.status(401).body(response);
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            boolean isAdmin = userDetails.getAuthorities().stream()
                       .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            Long currentUserId = userDetails.getId();

            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

            List<Reservation> reservations;

            if (isAdmin) {
                  reservations = reservationRepository.findAll().stream()
                             .filter(r -> status == null || (r.getStatus() != null && r.getStatus().name().equalsIgnoreCase(status)))
                             .filter(r -> minPrice == null || (r.getPrice() != null && r.getPrice().doubleValue() >= minPrice))
                             .filter(r -> maxPrice == null || (r.getPrice() != null && r.getPrice().doubleValue() <= maxPrice))
                             .skip(pageable.getOffset())
                             .limit(pageable.getPageSize())
                             .toList();
            } else {
                  reservations = reservationRepository.findByUserId(currentUserId).stream()
                             .filter(r -> status == null || (r.getStatus() != null && r.getStatus().name().equalsIgnoreCase(status)))
                             .filter(r -> minPrice == null || (r.getPrice() != null && r.getPrice().doubleValue() >= minPrice))
                             .filter(r -> maxPrice == null || (r.getPrice() != null && r.getPrice().doubleValue() <= maxPrice))
                             .skip(pageable.getOffset())
                             .limit(pageable.getPageSize())
                             .toList();
            }

            List<ReservationResponseDto> dtoList = reservations.stream().map(r -> {
                  ReservationResponseDto dto = new ReservationResponseDto();
                  dto.setId(r.getId());
                  dto.setStatus(r.getStatus());
                  dto.setPrice(r.getPrice());
                  dto.setStartTime(r.getStartTime());
                  dto.setEndTime(r.getEndTime());
                  dto.setUsername(r.getUser().getUsername());
                  dto.setResourceName(r.getResource().getName());
                  return dto;
            }).toList();

            response.responseMethod(HttpStatus.OK.value(), "Reservations fetched successfully", dtoList);
            return ResponseEntity.ok(response);
      }

      @Override
      public ResponseEntity<ApiResponse<ReservationResponseDto>> getReservationById(Long id) {
            ApiResponse<ReservationResponseDto> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
                  response.responseMethod(401, "Unauthorized", null);
                  return ResponseEntity.status(401).body(response);
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            Long currentUserId = userDetails.getId();
            boolean admin = userDetails.getAuthorities().stream()
                       .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            Optional<Reservation> reservationOpt = reservationRepository.findById(id);
            if (reservationOpt.isEmpty()) {
                  response.responseMethod(404, "Reservation not found", null);
                  return ResponseEntity.status(404).body(response);
            }

            Reservation reservation = reservationOpt.get();

            if (!admin && !reservation.getUser().getId().equals(currentUserId)) {
                  response.responseMethod(403, "Access denied", null);
                  return ResponseEntity.status(403).body(response);
            }

            ReservationResponseDto dto = new ReservationResponseDto();
            dto.setId(reservation.getId());
            dto.setStatus(reservation.getStatus());
            dto.setPrice(reservation.getPrice());
            dto.setStartTime(reservation.getStartTime());
            dto.setEndTime(reservation.getEndTime());
            dto.setCreatedAt(reservation.getCreatedDate() != null ? reservation.getCreatedDate().toInstant(ZoneOffset.UTC) : null);
            dto.setUpdatedAt(reservation.getLastModifiedDate() != null ? reservation.getLastModifiedDate().toInstant(ZoneOffset.UTC) : null);
            dto.setUserId(reservation.getUser().getId());
            dto.setUsername(reservation.getUser().getUsername());
            dto.setResourceId(reservation.getResource().getId());
            dto.setResourceName(reservation.getResource().getName());

            response.responseMethod(200, "Reservation fetched successfully", dto);
            return ResponseEntity.ok(response);
      }

      @Override
      public ResponseEntity<ApiResponse<ReservationResponseDto>> updateReservation(Long id, ReservationRequestDto dto) {
            ApiResponse<ReservationResponseDto> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
                  response.responseMethod(401, "Unauthorized", null);
                  return ResponseEntity.status(401).body(response);
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            Long currentUserId = userDetails.getId();
            boolean isAdmin = userDetails.getAuthorities().stream()
                       .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            Optional<Reservation> reservationOpt = reservationRepository.findById(id);
            if (reservationOpt.isEmpty()) {
                  response.responseMethod(404, "Reservation not found", null);
                  return ResponseEntity.status(404).body(response);
            }

            Reservation reservation = reservationOpt.get();

            if (!isAdmin && !reservation.getUser().getId().equals(currentUserId)) {
                  response.responseMethod(403, "Access denied", null);
                  return ResponseEntity.status(403).body(response);
            }

            if (dto.getStatus() != null) reservation.setStatus(dto.getStatus());
            if (dto.getPrice() != null) reservation.setPrice(dto.getPrice());
            if (dto.getStartTime() != null) reservation.setStartTime(dto.getStartTime());
            if (dto.getEndTime() != null) reservation.setEndTime(dto.getEndTime());

            if (dto.getResourceId() != null) {
                  Optional<Resources> resourceOpt = resourceRepository.findById(dto.getResourceId());
                  if (resourceOpt.isEmpty()) {
                        response.responseMethod(404, "Resource not found", null);
                        return ResponseEntity.status(404).body(response);
                  }
                  reservation.setResource(resourceOpt.get());
            }

            Reservation updatedReservation = reservationRepository.save(reservation);

            ReservationResponseDto dtoResponse = new ReservationResponseDto();
            dtoResponse.setId(updatedReservation.getId());
            dtoResponse.setStatus(updatedReservation.getStatus());
            dtoResponse.setPrice(updatedReservation.getPrice());
            dtoResponse.setStartTime(updatedReservation.getStartTime());
            dtoResponse.setEndTime(updatedReservation.getEndTime());
            dtoResponse.setCreatedAt(updatedReservation.getCreatedDate() != null ? updatedReservation.getCreatedDate().toInstant(ZoneOffset.UTC) : null);
            dtoResponse.setUpdatedAt(updatedReservation.getLastModifiedDate() != null ? updatedReservation.getLastModifiedDate().toInstant(ZoneOffset.UTC) : null);
            dtoResponse.setUserId(updatedReservation.getUser().getId());
            dtoResponse.setUsername(updatedReservation.getUser().getUsername());
            dtoResponse.setResourceId(updatedReservation.getResource().getId());
            dtoResponse.setResourceName(updatedReservation.getResource().getName());

            response.responseMethod(200, "Reservation updated successfully", dtoResponse);
            return ResponseEntity.ok(response);
      }

      @Override
      public ResponseEntity<ApiResponse<String>> deleteReservation(Long id) {
            ApiResponse<String> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
                  response.responseMethod(401, "Unauthorized", null);
                  return ResponseEntity.status(401).body(response);
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            Long currentUserId = userDetails.getId();
            boolean isAdmin = userDetails.getAuthorities().stream()
                       .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            Optional<Reservation> reservationOpt = reservationRepository.findById(id);
            if (reservationOpt.isEmpty()) {
                  response.responseMethod(404, "Reservation not found", null);
                  return ResponseEntity.status(404).body(response);
            }

            Reservation reservation = reservationOpt.get();

            if (!isAdmin && !reservation.getUser().getId().equals(currentUserId)) {
                  response.responseMethod(403, "Access denied", null);
                  return ResponseEntity.status(403).body(response);
            }

            reservationRepository.delete(reservation);
            response.responseMethod(200, "Reservation deleted successfully", "Deleted reservation id: " + id);
            return ResponseEntity.ok(response);
      }

}
