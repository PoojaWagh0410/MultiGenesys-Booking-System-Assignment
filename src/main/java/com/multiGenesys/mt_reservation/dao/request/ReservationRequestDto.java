package com.multiGenesys.mt_reservation.dao.request;

import com.multiGenesys.mt_reservation.Enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ReservationRequestDto {

      @NotNull(message = "Reservation Status cannot be null")
      private ReservationStatus status;

      private BigDecimal price;

      private Instant startTime;

      private Instant endTime;

      @NotNull(message = "Reservation id cannot be null")
      private Long resourceId;

      @NotNull(message = "User id  cannot be null")
      private Long userId;
}
