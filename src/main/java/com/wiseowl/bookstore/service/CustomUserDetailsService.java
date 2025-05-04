package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.entity.User;
import com.wiseowl.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from DB based on email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));

        System.out.println("Email found: " + user.getEmail());
        System.out.println("Password hash: " + user.getPassword());
        System.out.println("Is admin? " + user.isAdmin());

        // Determine user roles (authorities)
        List<SimpleGrantedAuthority> authorities = user.isAdmin() ?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Return Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
