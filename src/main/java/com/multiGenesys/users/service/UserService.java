package com.multiGenesys.users.service;

import com.multiGenesys.users.dto.request.LoginRequestDto;
import com.multiGenesys.users.dto.request.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

      ResponseEntity<?> createUser(UserRequestDto userRequestDto);

      ResponseEntity<?> updateUser(Long id, UserRequestDto userRequestDto);

      ResponseEntity<?> deleteUser(Long id);

      ResponseEntity<?> getUserById(Long id);

      ResponseEntity<?> login(LoginRequestDto loginRequestDto);

}
