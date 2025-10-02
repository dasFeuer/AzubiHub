package com.project.AzubiHub.dto;

import com.project.AzubiHub.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoSummary {

    private Long id;
    private String name;
    private String email;
    private Roles role;

}