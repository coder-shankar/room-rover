package com.room.booking.room.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface BookingMapper {


    @Mapping(target = "room", source = "booking.room.name")
    @Mapping(target = "roomId", source = "booking.room.id")
    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "bookingFrom", source = "bookingFrom")
    @Mapping(target = "bookingTo", source = "bookingTo")
    @Mapping(target = "purpose", source = "purpose")
    BookingDto toBookingDto(Booking booking);

    List<BookingDto> toBookingDto(List<Booking> booking);

}
