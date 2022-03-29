package com.project.sample.api.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

@Getter
public class MyUserDetails implements UserDetails {

    @Delegate
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public MyUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Optional.ofNullable(user).filter(s -> s.isUnregister()).isPresent();
    }

    @Override
    public boolean isAccountNonLocked() {
        return Optional.ofNullable(user).filter(s -> s.isUnregister()).isPresent();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Optional.ofNullable(user).filter(s -> s.isUnregister()).isPresent();
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(user).filter(s -> s.isUnregister()).isPresent();
    }
}