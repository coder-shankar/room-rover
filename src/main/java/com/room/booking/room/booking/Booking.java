package com.room.booking.room.booking;

import com.room.booking.room.Room;
import com.room.booking.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@SoftDelete
public class Booking extends PanacheEntity {

    @Column(name = "booking_from")
    LocalDateTime bookingFrom;

    @Column(name = "booking_to")
    LocalDateTime bookingTo;

    String purpose;

    @ManyToOne
    Room room;

    @ManyToOne
    User user;

}
