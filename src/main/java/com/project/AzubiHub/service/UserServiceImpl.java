package com.project.AzubiHub.service;

import com.project.AzubiHub.Enum.Roles;
import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    @Transactional
    @Override
    public User createNewUser(RegisterUserRequest registerUserRequest) {
        User newUser = new User();
        newUser.setName(registerUserRequest.getName());
        newUser.setEmail(registerUserRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        newUser.setRole(Roles.AZUBI_USER);

        return userRepository.save(newUser);
    }
}
