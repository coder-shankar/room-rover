package com.room.booking.user;

import com.room.booking.auth.Role;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_info")
public class User extends PanacheEntity {

    public String username;
    public String password;
    public String name;
    public Set<Role> roles;

}
