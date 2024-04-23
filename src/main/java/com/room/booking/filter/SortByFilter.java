package com.room.booking.filter;

import lombok.Getter;

@Getter
public class SortByFilter {

    private String attribute;
    private SortByType type = SortByType.ASC;

    public enum SortByType {
        ASC, DESC
    }

    public SortByFilter(String sort) {
        if (sort != null) {
            String[] split = sort.split(":");
            if (split.length != 2) {
                throw new RuntimeException("Cannot extract sorting parameter");
            }

            attribute = split[0];
            type = SortByType.valueOf(split[1].toUpperCase());
        }
    }
}
