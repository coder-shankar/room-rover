package com.room.booking.room;

import com.room.booking.room.booking.BookingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "jakarta", uses = BookingMapper.class)
public interface RoomMapper {

    @Mapping(target = "booked", source = "bookings")
    RoomDto toRoomDto(Room room);

    List<RoomDto> toRoomDtoList(List<Room> rooms);

}
