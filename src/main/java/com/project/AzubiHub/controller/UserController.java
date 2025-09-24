package com.project.AzubiHub.controller;

import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.RegisterUserRequestDto;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.enitty.UserDto;
import com.project.AzubiHub.mapper.UserMapper;
import com.project.AzubiHub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createNewUser(
            @Valid  @RequestBody RegisterUserRequestDto registerUserRequestDto){
        RegisterUserRequest registerUserRequest = userMapper.toRegister(registerUserRequestDto);
        User newUser = userService.createNewUser(registerUserRequest);
        UserDto userDto = userMapper.toDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
