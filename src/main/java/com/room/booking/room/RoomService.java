package com.room.booking.room;

import com.room.booking.filter.DateFilter;
import com.room.booking.filter.TimeFilter;
import com.room.booking.room.booking.RoomQueryParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class RoomService {

    @Inject
    RoomRepository repository;

    @Inject
    RoomMapper mapper;

    @Transactional
    public RoomDto createRoom(RoomDto dto) {
        final Room room = new Room();
        room.setName(dto.name);
        room.setCapacity(dto.capacity);
        repository.persistAndFlush(room);
        dto.setId(room.id.toString());
        return dto;
    }

    public Room findRoom(String id) {
        return repository.findById(Long.parseLong(id));
    }

    public List<RoomDto> filter(RoomQueryParam param) {
        var filters = new HashMap<String, Object>();
        filters.put("bookings|bookingFrom", new DateFilter(param.getDate(), param.getDate()));
        filters.put("bookings|bookingTo", new DateFilter(param.getDate(), param.getDate()));
        filters.put("capacity", param.getCapacity());

        final List<Room> rooms = repository.filter(filters);
        return mapper.toRoomDtoList(rooms);
    }

    @Transactional
    public RoomDto updateRoom(RoomDto roomDto) {
        final Room room = findRoom(roomDto.getId());
        room.setName(roomDto.name);
        room.setCapacity(roomDto.capacity);
        repository.persist(room);

        return mapper.toRoomDto(room);
    }

    @Transactional
    public void delete(RoomDto roomDto) {
        final Room room = findRoom(roomDto.getId());
        repository.delete(room);
    }
}
