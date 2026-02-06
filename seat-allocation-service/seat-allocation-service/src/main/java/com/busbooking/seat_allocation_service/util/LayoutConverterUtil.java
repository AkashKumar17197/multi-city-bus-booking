package com.busbooking.seat_allocation_service.util;

import java.util.ArrayList;
import java.util.List;

public class LayoutConverterUtil {

    public static String joinList(List<String> list) {
        return list == null ? null : String.join(" | ", list);
    }

    public static List<String> splitString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(value.split("\\s*\\|\\s*"));
    }
}
