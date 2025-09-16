package com.multiGenesys.mt_resources.dao.response;

import com.multiGenesys.mt_reservation.entity.Reservation;
import com.multiGenesys.mt_resources.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResourceResponseDto {

      private Long id;

      private String name;

      private Type type;

      private String description;

      private Long capacity;

      private Boolean active;

}
