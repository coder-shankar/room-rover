package com.room.booking.room.booking;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BookingDto {
    String room;
    Long roomId;
    Long userId;
    LocalDateTime bookingFrom;
    LocalDateTime bookingTo;
    String purpose;

    String bookingId;

}
