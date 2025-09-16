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
import jakarta.persistence.EntityNotFoundException;
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
      public ResponseEntity<?> createUser(UserRequestDto userRequestDto) {
            Users user = mapperUtil.toEntity(userRequestDto, Users.class);
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            Users savedUser = userRepository.save(user);
            UserResponseDto responseDto = mapperUtil.toDto(savedUser, UserResponseDto.class);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
      }

      @Override
      public ResponseEntity<?> updateUser(Long id, UserRequestDto userRequestDto) {
            Users existingUser = userRepository.findById(id)
                       .orElseThrow(() -> new EntityNotFoundException("User not found"));
            existingUser.setUsername(userRequestDto.getUsername());
            existingUser.setPassword(userRequestDto.getPassword());
            existingUser.setRole(userRequestDto.getRole());
            existingUser.setEnabled(userRequestDto.isEnabled());
            Users updatedUser = userRepository.save(existingUser);
            UserResponseDto responseDto = mapperUtil.toDto(updatedUser, UserResponseDto.class);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<?> deleteUser(Long id) {
            if (!userRepository.existsById(id)) {
                  return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
      }

      @Override
      public ResponseEntity<?> getUserById(Long id) {
            Users user = userRepository.findById(id)
                       .orElseThrow(() -> new EntityNotFoundException("User not found"));
            UserResponseDto responseDto = mapperUtil.toDto(user, UserResponseDto.class);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
            var response = new ApiResponse<>();

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
                              response.responseMethod(HttpStatus.OK.value(), "Login successfully", userResponseDto, null);
                        } else {
                              response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null, null);
                        }
                  } else {
                        response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null, null);
                  }
            } else {
                  response.responseMethod(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", null, null);
            }
            return ResponseEntity.ok(response);
      }

}