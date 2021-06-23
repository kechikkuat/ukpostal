package com.geopostal.ukpostal.controllers.users;

import com.geopostal.ukpostal.model.User;
import com.geopostal.ukpostal.services.users.UserAuthService;
import com.geopostal.ukpostal.services.users.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {
    @NonNull UserAuthService authService;
    @NonNull UserService userService;

    @PostMapping("public/api/users/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password){

        userService.save(User.builder()
                .id(username)
                .username(username)
                .password(password)
                .build());

        return login(username, password);
    }

    @PostMapping("public/api/users/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){

        return authService.login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid username and/or password"));
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final User user) {
        authService.logout(user);
        return true;
    }
}
