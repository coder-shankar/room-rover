package com.room.booking.room.booking;

import com.room.booking.room.Room;
import com.room.booking.room.RoomService;
import com.room.booking.user.User;
import com.room.booking.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.List;

@ApplicationScoped
public class BookingService {
    @Inject
    BookingRepository repository;

    @Inject
    BookingMapper mapper;

    @Inject
    UserService userService;

    @Inject
    RoomService roomService;

    public List<BookingDto> getBookingHistory(User user) {
        return mapper.toBookingDto(repository.listBooking(user));
    }

    public List<BookingDto> getUpcoming(User user) {
        return mapper.toBookingDto(repository.listUpcomingBooking(user));
    }

    @Transactional
    public BookingDto book(BookingDto dto) {
        final User user = userService.findById(dto.getUserId().toString());
        final Room room = roomService.findRoom(dto.getRoomId().toString());

        final Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setBookingFrom(dto.getBookingFrom());
        booking.setBookingTo(dto.getBookingTo());
        booking.setBookingFrom(dto.getBookingFrom());
        booking.setPurpose(dto.getPurpose());

        repository.persist(booking);

        return mapper.toBookingDto(booking);
    }

    Booking findById(String id) {
        final Booking byId = repository.findById(Long.parseLong(id));
        if (byId == null) {
            throw new BadRequestException("Booking with id " + id + " not found");
        }

        return byId;
    }

    @Transactional
    public BookingDto updateBooking(BookingDto dto) {
        final Booking booking = findById(dto.bookingId);

        if (dto.getUserId() != null) {
            final User user = userService.findById(dto.getUserId().toString());
            booking.setUser(user);
        }

        if (dto.getRoomId() != null) {
            final Room room = roomService.findRoom(dto.getRoomId().toString());
            booking.setRoom(room);
        }

        booking.setBookingFrom(dto.getBookingFrom());
        booking.setBookingTo(dto.getBookingTo());
        booking.setBookingFrom(dto.getBookingFrom());
        booking.setPurpose(dto.getPurpose());

        repository.persist(booking);

        return mapper.toBookingDto(booking);
    }


    @Transactional
    public void delete(BookingDto dto) {
        final Booking booking = findById(dto.getBookingId());
        repository.delete(booking);
    }
}
