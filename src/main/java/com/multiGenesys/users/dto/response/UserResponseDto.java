package com.multiGenesys.users.dto.response;

import com.multiGenesys.users.enums.Role;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserResponseDto {

      private Long id;

      private String username;

      private Role role;

      private boolean enabled;
}


