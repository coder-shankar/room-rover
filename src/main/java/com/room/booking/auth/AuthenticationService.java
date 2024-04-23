package com.room.booking.auth;

import com.room.booking.user.User;
import com.room.booking.user.UserDetailDto;
import com.room.booking.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthenticationService {
    @Inject
    PBKDF2Encoder passwordEncoder;

    @ConfigProperty(name = "quarkusjwt.jwt.duration.second")
    public Long duration;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    @Inject
    UserService userService;

    public AuthResponse login(AuthRequest authRequest) {
        Optional<User> user = userService.findByUsername(authRequest.username);
        if (user.isPresent() && user.get().password.equals(passwordEncoder.encode(authRequest.password))) {
            try {
                final String token = TokenUtils.generateToken(user.get().username, user.get().roles, duration, issuer);
                return new AuthResponse(token);
            } catch (Exception e) {
                throw new NotAuthorizedException("Invalid username or password");
            }
        } else {
            throw new NotAuthorizedException("Invalid username or password");
        }
    }

    @Transactional
    public UserDetailDto signup(UserDetailDto userDetailDto) {
        userService.findByUsername(userDetailDto.email)
                   .ifPresent(user -> {
                       throw new NotAuthorizedException("Username already exists");
                   });
        final User user = new User();
        user.username = userDetailDto.getEmail();
        user.password = passwordEncoder.encode(userDetailDto.password);
        user.name = userDetailDto.name;
        user.roles = Set.of(Role.USER);

        userService.save(user);
        userDetailDto.id = user.id.toString();

        return userDetailDto;
    }
}
