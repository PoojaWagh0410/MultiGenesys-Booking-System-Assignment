package com.multiGenesys.users.dto.request;

import com.multiGenesys.users.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

      private String username;

      private String password;

      private Role role;

      private boolean enabled;
}
