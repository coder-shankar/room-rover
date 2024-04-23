package com.room.booking.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateFilter {
    private String from;
    private String to;

}
