package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.dto.UserDto;
import com.wiseowl.bookstore.entity.User;
import com.wiseowl.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;

    // Hash the password before saving
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserDto userDto) {
        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // hashed password
        user.setAdmin(false); // default user role

        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
