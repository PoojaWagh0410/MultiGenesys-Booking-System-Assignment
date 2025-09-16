package com.multiGenesys.mt_reservation.entity;

import com.multiGenesys.common.BaseEntity;
import com.multiGenesys.mt_reservation.Enums.ReservationStatus;
import com.multiGenesys.mt_resources.entity.Resources;
import com.multiGenesys.users.entity.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Data
@Table(name="mt_reservation")
public class Reservation extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Enumerated(EnumType.STRING)
      private ReservationStatus status;

      private BigDecimal price;

      private Instant startTime;

      private Instant endTime;

      private Instant createdAt;

      private Instant updatedAt;

      @ManyToOne
      @JoinColumn(name = "resource_id", nullable = false)
      private Resources resource;

      @ManyToOne
      @JoinColumn(name = "user_id", nullable = false)
      private Users user;
}
