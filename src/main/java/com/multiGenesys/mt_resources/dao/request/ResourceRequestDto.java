package com.multiGenesys.mt_resources.dao.request;

import com.multiGenesys.mt_resources.enums.Type;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceRequestDto {

      @NotNull(message = "Name of resource cannot be null")
      private String name;

      @NotNull(message = "Type of resource cannot be null")
      private Type type;

      private String description;

      @NotNull(message = "capacity of resource cannot be null")
      private Long capacity;

      private Boolean active;

}
