package com.geopostal.ukpostal.services.users;

import com.geopostal.ukpostal.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UUIDAuthServiceImpl implements UserAuthService{
    @NonNull
    UserService userService;

    @Override
    public Optional<String> login(String username, String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .build();

        userService.save(user);
        return Optional.of(uuid);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userService.find(token);
    }

    @Override
    public void logout(User user) {

    }
}
