package com.multiGenesys.mt_resources.entity;

import com.multiGenesys.common.BaseEntity;
import com.multiGenesys.mt_reservation.entity.Reservation;
import com.multiGenesys.mt_resources.enums.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name="mt_resources")
public class Resources extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(nullable = false)
      private String name;

      @Enumerated(EnumType.STRING)
      private Type type;

      private String description;

      private Long capacity;

      private Boolean active;

      @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
      private List<Reservation> reservations;

}
