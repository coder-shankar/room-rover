package com.room.booking.room;

import com.room.booking.filter.FilterDao;
import com.room.booking.room.booking.Booking;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomRepository extends FilterDao<Room> implements PanacheRepository<Room> {
    public RoomRepository() {
        super(Room.class);
    }
}
