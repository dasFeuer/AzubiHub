package com.project.AzubiHub.mapper;

import com.project.AzubiHub.enitty.RegisterUserRequest;
import com.project.AzubiHub.enitty.RegisterUserRequestDto;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.enitty.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper{

    UserDto toDto(User user);
    RegisterUserRequest toRegister(RegisterUserRequestDto registerUserRequestDto);
}
