package com.multiGenesys.users.dto.response;

import com.multiGenesys.users.enums.Role;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserResponseDto {

      private String username;

      private String password;

      private Role role;

      private boolean enabled;
}


