package com.room.booking.user;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class UserDetailDto {

    @Email
    public String email;

    @Getter(AccessLevel.PRIVATE)
    public String password;

    public String name;
    public String phone;

    //response
    public String id;
}
