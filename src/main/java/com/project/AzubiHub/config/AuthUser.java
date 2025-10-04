package com.project.AzubiHub.config;

import com.project.AzubiHub.Enum.Roles;
import com.project.AzubiHub.enitty.User;
import com.project.AzubiHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUser {
    private final UserService userService;

    public Optional<User> getAuthenticateCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

    public User getAuthenticatedUserOrThrow() {
        return getAuthenticateCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));
    }

    public void validateUserAccess(Long userId){
        User user = getAuthenticatedUserOrThrow();

        boolean isAdmin = user.getRole().equals(Roles.ADMIN_USER);

        if (isAdmin){
            return;
        }

        if (!user.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
    }

}
