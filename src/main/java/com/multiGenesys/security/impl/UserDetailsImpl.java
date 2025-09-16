package com.multiGenesys.security.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multiGenesys.users.entity.Users;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@ToString
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;

    @JsonIgnore
    private final String password;

    private final boolean enabled;
    private final GrantedAuthority authority;

    public UserDetailsImpl(Long id, String username, String password, boolean enabled,
                           GrantedAuthority authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled = enabled;
    }

    public static UserDetailsImpl build(Users user) {
        // Take role from entity
        String role = user.getRole().name(); // e.g. "ADMIN" or "USER"
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);

        return new UserDetailsImpl(
                   user.getId(),
                   user.getUsername(),
                   user.getPassword(),
                   user.isEnabled(),
                   grantedAuthority
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }
}
