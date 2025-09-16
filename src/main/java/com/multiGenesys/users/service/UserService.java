package com.multiGenesys.users.service;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.users.dto.request.LoginRequestDto;
import com.multiGenesys.users.dto.request.UserRequestDto;
import com.multiGenesys.users.dto.response.LoginUserResponseDto;
import com.multiGenesys.users.dto.response.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

      ResponseEntity<ApiResponse<UserResponseDto>> createUser(UserRequestDto userRequestDto);

      ResponseEntity<ApiResponse<UserResponseDto>> updateUser(Long id, UserRequestDto userRequestDto);

      ResponseEntity<ApiResponse<Void>> deleteUser(Long id);

      ResponseEntity<ApiResponse<UserResponseDto>> getUserById(Long id);

      ResponseEntity<ApiResponse<LoginUserResponseDto>> login(LoginRequestDto loginRequestDto);

}
