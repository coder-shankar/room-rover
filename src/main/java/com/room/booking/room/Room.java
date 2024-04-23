package com.room.booking.room;

import com.room.booking.room.booking.Booking;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@Entity
@SoftDelete
public class Room extends PanacheEntity {
    String name;
    int capacity;
    String description;
    String address;

    @OneToMany
    @Fetch(FetchMode.JOIN)
    List<Booking> bookings;
}
