package com.project.AzubiHub.controller;

import com.project.AzubiHub.dto.AuthResponse;
import com.project.AzubiHub.enitty.LoginUserRequest;
import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.RegisterUserRequestDto;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.dto.UserDto;
import com.project.AzubiHub.mapper.UserMapper;
import com.project.AzubiHub.service.UserService;
import com.project.AzubiHub.util.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createNewUser(
            @Valid  @RequestBody RegisterUserRequestDto registerUserRequestDto){
        RegisterUserRequest registerUserRequest = userMapper.toRegister(registerUserRequestDto);
        User newUser = userService.createNewUser(registerUserRequest);
        UserDto userDto = userMapper.toDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(
            @Valid @RequestBody LoginUserRequest loginUserRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getEmail(),
                        loginUserRequest.getPassword()
                )
        );

        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        AuthResponse loginResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400L)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

}
