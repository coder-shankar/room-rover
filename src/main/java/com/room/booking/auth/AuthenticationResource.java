package com.room.booking.auth;

import com.room.booking.user.UserDetailDto;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @Inject
    AuthenticationService service;

    @PermitAll
    @POST
    @Path("/login")
    public AuthResponse login(AuthRequest authRequest) {
        return service.login(authRequest);
    }

    @PermitAll
    @POST
    @Path("/signup")
    public UserDetailDto signup(UserDetailDto userDetailDto) {
        return service.signup(userDetailDto);
    }

}
