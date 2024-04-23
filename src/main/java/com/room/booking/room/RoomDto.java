package com.room.booking.room;

import com.room.booking.room.booking.BookingDto;
import lombok.Data;

import java.util.List;

@Data
public class RoomDto {
    String name;
    int capacity;

    List<BookingDto> booked;

    //response only
    String id;
}
