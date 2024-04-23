package com.room.booking.room.booking;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class RoomQueryParam {
    @QueryParam("date")
    String date;

    @QueryParam("fromTime")
    String fromTime;

    @QueryParam("toTime")
    String toTime;

    @QueryParam("capacity")
    Integer capacity;
}
