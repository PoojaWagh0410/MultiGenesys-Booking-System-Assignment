package com.multiGenesys.security.impl;

import com.multiGenesys.users.entity.Users;
import com.multiGenesys.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookingUserDetails implements UserDetailsService {
      private static final Logger logger = LoggerFactory.getLogger(BookingUserDetails.class);

      @Autowired
      private UserRepository userRepository;

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try {
                  Users user = (Users) userRepository.findByUsernameAndEnabledTrue(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

                  return UserDetailsImpl.build(user);
            } catch (UsernameNotFoundException ex) {
                  logger.error("User not found: {}", username, ex);
                  throw ex;
            }
      }
}