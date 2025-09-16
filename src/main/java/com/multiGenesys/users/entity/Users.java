package com.multiGenesys.users.entity;

import com.multiGenesys.common.BaseEntity;
import com.multiGenesys.users.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
@Table(name = "users")
public class Users extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(unique = true, nullable = false)
      private String username;

      @Column(nullable = false)
      private String password;

      @Enumerated(EnumType.STRING)
      private Role role;

      @Column(nullable = false)
      private boolean enabled;
}
