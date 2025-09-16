package com.multiGenesys.mt_reservation.dao.response;

import com.multiGenesys.mt_reservation.Enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class ReservationResponseDto {

      private Long id;

      private ReservationStatus status;

      private BigDecimal price;

      private Instant startTime;

      private Instant endTime;

      private Instant createdAt;

      private Instant updatedAt;

      private Long resourceId;

      private String resourceName;

      private Long userId;

      private String username;

}
