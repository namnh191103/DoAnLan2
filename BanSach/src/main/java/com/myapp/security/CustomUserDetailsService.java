package com.myapp.security;

import com.myapp.model.User;
import com.myapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        log.info("=== START: loadUserByUsername ===");
        log.info("Attempting to load user with email: '{}', raw='{}'", usernameOrEmail, usernameOrEmail);
        
        try {
            User user = userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", usernameOrEmail);
                    return new UsernameNotFoundException("User not found with email: " + usernameOrEmail);
                });

            log.info("Found user: {}", user.getEmail());
            log.info("User details: id={}, name={}, email={}, password={}", 
                user.getId(), user.getHoTen(), user.getEmail(), user.getMatKhau());
            
            if (user.isDaKhoa()) {
                log.warn("User account is locked: {}", usernameOrEmail);
                throw new LockedException("User account is locked.");
            }

            Collection<? extends GrantedAuthority> authorities = user.getVaiTros().stream()
                .map(role -> new SimpleGrantedAuthority(role.getTenVaiTro()))
                .collect(Collectors.toList());
            
            log.info("Created UserDetails with authorities: {}", authorities);
            
            log.info("=== END: loadUserByUsername ===");
            return new CustomUserDetails(user);
        } catch (Exception e) {
            log.error("Error loading user by email: {}", usernameOrEmail, e);
            throw e;
        }
    }
} 