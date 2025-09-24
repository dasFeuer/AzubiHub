package com.project.AzubiHub.service;

import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.User;

public interface UserService {
    User createNewUser(RegisterUserRequest registerUserRequest);
}
