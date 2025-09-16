package com.multiGenesys.users.controller;

import com.multiGenesys.users.dto.request.LoginRequestDto;
import com.multiGenesys.users.dto.request.UserRequestDto;
import com.multiGenesys.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

      private final UserService userService;

      @PostMapping("/users")
      public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) {
            return userService.createUser(userRequestDto);
      }

      @PutMapping("/users/{id}")
      public ResponseEntity<?> updateUser(
                 @PathVariable Long id,
                 @RequestBody UserRequestDto userRequestDto
      ) {
            return userService.updateUser(id, userRequestDto);
      }

      @DeleteMapping("/users/{id}")
      public ResponseEntity<?> deleteUser(@PathVariable Long id) {
            return userService.deleteUser(id);
      }

      @GetMapping("/users/{id}")
      public ResponseEntity<?> getUserById(@PathVariable Long id) {
            return userService.getUserById(id);
      }

      @PostMapping("/auth/login")
      public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
            return userService.login(loginRequestDto);
      }
}
