package com.project.AzubiHub.service;

import com.project.AzubiHub.Enum.Roles;
import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.UpdateUserRequest;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Override
    public Optional<User> findUserById(Long Id) {
        return userRepository.findById(Id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        return userRepository.findByEmail(email);
    }


    @Override
    public User updateUser(Long Id, UpdateUserRequest updateUserRequest) {
        Optional<User> existedUser = userRepository.findById(Id);
        if (existedUser.isPresent()) {
            User updateUser = existedUser.get();
            updateUser.setName(updateUserRequest.getName());
            updateUser.setEmail(updateUserRequest.getEmail());
            updateUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

            return userRepository.save(updateUser);

        }
        throw new UsernameNotFoundException("User ID cannot be found: " + Id);
    }

    @Override
    public void deleteUserById(Long Id) {
        Optional<User> user = userRepository.findById(Id);
        if(user.isPresent()) {
            User deleteUser = user.get();
            userRepository.deleteById(deleteUser.getId());
        } else {
            throw new UsernameNotFoundException("User ID cannot be found: " + Id);
        }
    }
}
