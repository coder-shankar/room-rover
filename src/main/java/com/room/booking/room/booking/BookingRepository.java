package com.room.booking.room.booking;

import com.room.booking.filter.FilterDao;
import com.room.booking.user.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class BookingRepository extends FilterDao<Booking> implements PanacheRepository<Booking> {

    public BookingRepository() {
        super(Booking.class);
    }

    public List<Booking> listBooking(User user) {
        return list("user = ?1 order by bookingFrom desc", user);
    }

    public List<Booking> listUpcomingBooking(User user) {
        return list("user = ?1  and bookingFrom >=?2 order by bookingFrom desc", user, LocalDateTime.now());
    }

}
