package com.room.booking.user;

import com.room.booking.room.booking.BookingDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResources {

    @Inject
    UserService service;

    @GET
    @Path("{id}")
    public UserDetailDto getUserInfo(@PathParam("id") String id) {
        return service.getUserInfo(id);
    }

    @GET
    @Path("{id}/history")
    public List<BookingDto> getBookingHistory(@PathParam("id") String id) {
        return service.getBookingHistory(id);
    }

    @GET
    @Path("{id}/upcoming")
    public List<BookingDto> getUpcomings(@PathParam("id") String id) {
        return service.getUpcoming(id);
    }

}
