package com.myapp.security;

import com.myapp.model.User;
import com.myapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = email.trim().toLowerCase();
        log.info("=== START: loadUserByUsername ===");
        log.info("Attempting to load user with email: '{}', raw='{}'", normalizedEmail, email);
        
        try {
            User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", normalizedEmail);
                    return new UsernameNotFoundException("User not found with email: " + normalizedEmail);
                });

            log.info("Found user: {}", user.getEmail());
            log.info("User details: id={}, name={}, email={}, password={}", 
                user.getId(), user.getHoTen(), user.getEmail(), user.getMatKhau());
            
            CustomUserDetails userDetails = new CustomUserDetails(user);
            log.info("Created UserDetails with authorities: {}", userDetails.getAuthorities());
            
            log.info("=== END: loadUserByUsername ===");
            return userDetails;
        } catch (Exception e) {
            log.error("Error loading user by email: {}", normalizedEmail, e);
            throw e;
        }
    }
} 