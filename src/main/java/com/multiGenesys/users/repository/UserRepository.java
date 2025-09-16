package com.multiGenesys.users.repository;

import com.multiGenesys.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long> {

      Optional<Users> findByUsername(String username);

      @Query(value = "SELECT u.role FROM Users u WHERE u.username = ?1")
      String getUserRoleByUsername(String username);

      Optional<Object> findByUsernameAndEnabledTrue(String username);
}
