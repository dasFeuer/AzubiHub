package com.project.AzubiHub.service;

import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.UpdateUserRequest;
import com.project.AzubiHub.enitty.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createNewUser(RegisterUserRequest registerUserRequest);
    Optional<User> findUserById(Long Id);
    Optional<User> getUserByEmail(String email);
    User updateUser(Long Id, UpdateUserRequest updateUserRequest);
    void deleteUserById(Long Id);
    List<User> getAllUser();


}
