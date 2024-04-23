package com.room.booking.room;

import com.room.booking.room.booking.RoomQueryParam;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/rooms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoomResource {

    @Inject
    RoomService service;


    @POST
    public RoomDto createRoom(RoomDto roomDto) {
        return service.createRoom(roomDto);
    }

    @GET
    public List<RoomDto> filter(@BeanParam RoomQueryParam param) {
        return service.filter(param);
    }

    @PATCH
    public RoomDto updateRoom(RoomDto roomDto) {
        return service.updateRoom(roomDto);
    }

    @DELETE
    public void delete(RoomDto roomDto) {
         service.delete(roomDto);
    }
}
