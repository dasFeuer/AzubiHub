package com.project.AzubiHub.enitty;

import com.project.AzubiHub.Enum.Roles;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Roles role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}