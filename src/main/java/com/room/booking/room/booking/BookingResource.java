package com.room.booking.room.booking;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    BookingService service;

    @POST
    public BookingDto book(BookingDto booking) {
        return service.book(booking);
    }

    @PATCH
    public BookingDto updateBooking(BookingDto booking) {
        return service.updateBooking(booking);
    }

    @DELETE
    public void deleteBooking(BookingDto booking) {
        service.delete(booking);
    }


}
