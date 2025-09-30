package com.project.AzubiHub.controller;

import com.project.AzubiHub.dto.AuthResponse;
import com.project.AzubiHub.dto.UpdateUserRequestDto;
import com.project.AzubiHub.enitty.LoginUserRequest;
import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.dto.RegisterUserRequestDto;
import com.project.AzubiHub.enitty.UpdateUserRequest;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        UserDto userCreated = userMapper.toDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
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

    @PutMapping("/{Id}/update")
    public ResponseEntity<UserDto> loginUser(
            @PathVariable Long Id,  @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto ) {
        UpdateUserRequest updateUserRequest = userMapper.toUpdate(updateUserRequestDto);
        User user = userService.updateUser(Id, updateUserRequest);
        UserDto userUpdated = userMapper.toDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @GetMapping("/{Id}/get")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long Id ) {
        Optional<User> user = userService.findUserById(Id);
        if (user.isPresent()) {
            return ResponseEntity.ok(userMapper.toDto(user.get()));
        }
        throw new UsernameNotFoundException("User not found with ID: " + Id);
    }

    @DeleteMapping("/{Id}/delete")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable Long Id ) {
        userService.deleteUserById(Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
