package com.room.booking.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeFilter {
    private String from;
    private String to;

}
