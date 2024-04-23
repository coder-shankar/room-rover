package com.room.booking.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationFilter {
    private final Integer page;
    private final Integer pageSize;
}
