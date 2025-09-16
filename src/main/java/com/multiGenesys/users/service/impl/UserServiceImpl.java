package com.multiGenesys.users.service.impl;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mapper.MapperUtil;
import com.multiGenesys.security.JwtUtils;
import com.multiGenesys.users.dto.request.LoginRequestDto;
import com.multiGenesys.users.dto.request.UserRequestDto;
import com.multiGenesys.users.dto.response.LoginUserResponseDto;
import com.multiGenesys.users.dto.response.UserResponseDto;
import com.multiGenesys.users.entity.Users;
import com.multiGenesys.users.repository.UserRepository;
import com.multiGenesys.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

      private final UserRepository userRepository;

      private final MapperUtil mapperUtil;

      private final PasswordEncoder passwordEncoder;

      private final JwtUtils jwtUtils;


      @Override
      public ResponseEntity<ApiResponse<UserResponseDto>> createUser(UserRequestDto userRequestDto) {

            ApiResponse<UserResponseDto> response = new ApiResponse<>();

            Users user = mapperUtil.toEntity(userRequestDto, Users.class);
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

            Users savedUser = userRepository.save(user);

            UserResponseDto responseDto = mapperUtil.toDto(savedUser, UserResponseDto.class);

            response.responseMethod(HttpStatus.CREATED.value(), "User created successfully", responseDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
      }

      @Override
      public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(Long id, UserRequestDto userRequestDto) {
            var response = new ApiResponse<UserResponseDto>();

            Optional<Users> existingUser = userRepository.findById(id);

            if (existingUser.isEmpty()) {
                  response.responseMethod(HttpStatus.NOT_FOUND.value(), "User not found", null);
                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Users user = existingUser.get();
            user.setUsername(userRequestDto.getUsername());
            if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                  user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            }
            user.setRole(userRequestDto.getRole());
            user.setEnabled(userRequestDto.isEnabled());

            Users updatedUser = userRepository.save(user);
            UserResponseDto responseDto = mapperUtil.toDto(updatedUser, UserResponseDto.class);

            response.responseMethod(HttpStatus.OK.value(), "User updated successfully", responseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<ApiResponse<Void>> deleteUser(Long id) {

            ApiResponse<Void> response = new ApiResponse<>();

            if (!userRepository.existsById(id)) {
                  response.responseMethod(HttpStatus.NOT_FOUND.value(), "User not found", null);
                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            userRepository.deleteById(id);
            response.responseMethod(HttpStatus.OK.value(), "User deleted successfully!", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(Long id) {

            ApiResponse<UserResponseDto> response = new ApiResponse<>();

            Optional<Users> user = userRepository.findById(id);
            if (user.isEmpty()) {
                  response.responseMethod(HttpStatus.NOT_FOUND.value(), "User not found", null);
                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            UserResponseDto responseDto = mapperUtil.toDto(user, UserResponseDto.class);
            response.responseMethod(HttpStatus.OK.value(), "User fetch successfully!", responseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<ApiResponse<LoginUserResponseDto>> login(LoginRequestDto loginRequestDto) {

            ApiResponse<LoginUserResponseDto> response = new ApiResponse<>();

            if (loginRequestDto.getUsername() != null || loginRequestDto.getPassword() != null) {

                  Optional<Users> getData = userRepository.findByUsername(loginRequestDto.getUsername());
                  if (!getData.isEmpty()) {
                        if (passwordEncoder.matches(loginRequestDto.getPassword(), getData.get().getPassword())) {

                              String token = jwtUtils.generateToken(getData.get().getUsername(), getData.get().getId());

                              //for return the login user response
                              LoginUserResponseDto userResponseDto = new LoginUserResponseDto();
                              userResponseDto.setUserName(getData.get().getUsername());
                              userResponseDto.setUserId(getData.get().getId());
                              userResponseDto.setToken(token);

                              userResponseDto.setRole(userRepository.getUserRoleByUsername(loginRequestDto.getUsername()));
                              //userResponseDto.setRole("Admin");
                              response.responseMethod(HttpStatus.OK.value(), "Login successfully", userResponseDto);
                        } else {
                              response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null);
                        }
                  } else {
                        response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null);
                  }
            } else {
                  response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null);
            }
            return ResponseEntity.ok(response);
      }



}