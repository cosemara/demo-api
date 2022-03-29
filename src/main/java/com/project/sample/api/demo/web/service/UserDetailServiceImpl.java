package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.domain.entity.MyUserDetails;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.UserRepository;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new CommonApiException(ExceptionCode.USER_NOT_FOUND));
        return MyUserDetails.builder().user(user).build();
    }

    public User authenticateByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CommonApiException(ExceptionCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CommonApiException(ExceptionCode.UNAUTHORIZED);
        }

        return user;
    }
}
