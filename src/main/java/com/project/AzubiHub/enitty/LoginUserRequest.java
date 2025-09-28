package com.project.AzubiHub.enitty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {
    private String email;
    private String password;
}
