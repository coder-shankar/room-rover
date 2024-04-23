package com.room.booking.user;

import com.room.booking.room.booking.BookingDto;
import com.room.booking.room.booking.BookingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    BookingService bookingService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        userRepository.persistAndFlush(user);
        return user;
    }

    public User findById(String id) {
        final User user = userRepository.findById(Long.parseLong(id));
        if (user == null) {
            throw new BadRequestException("User not found");
        }
        return user;
    }

    public UserDetailDto getUserInfo(String id) {
        final User byId = findById(id);
        final UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.email = byId.username;
        userDetailDto.name = byId.name;
        userDetailDto.id = byId.id.toString();
        return userDetailDto;
    }

    public List<BookingDto> getBookingHistory(String userId) {
        final User byId = findById(userId);
        return bookingService.getBookingHistory(byId);
    }

    public List<BookingDto> getUpcoming(String id) {
        final User byId = findById(id);
        return bookingService.getUpcoming(byId);
    }
}
