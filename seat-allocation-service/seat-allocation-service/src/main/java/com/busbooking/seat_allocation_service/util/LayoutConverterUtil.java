package com.busbooking.seat_allocation_service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LayoutConverterUtil {

    // Join a list of strings with " | "
    public static String joinList(List<String> list) {
        return list == null ? null : String.join(" | ", list);
    }

    // Split a joined string into a list
    public static List<String> splitString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(value.split("\\s*\\|\\s*"));
    }

    // ------------------ NEW ------------------
    // Convert a flat string layout ("A B C | D E F") → 2D List
    public static List<List<String>> to2DList(String value) {
        List<List<String>> result = new ArrayList<>();
        if (value == null || value.isEmpty()) return result;

        String[] rows = value.split("\\s*\\|\\s*");
        for (String row : rows) {
            result.add(new ArrayList<>(Arrays.asList(row.split("\\s+"))));
        }
        return result;
    }

    // Convert 2D List → flat string layout for DB
    public static String join2DList(List<List<String>> lists) {
        List<String> rows = new ArrayList<>();
        for (List<String> row : lists) {
            rows.add(String.join(" ", row));
        }
        return String.join(" | ", rows);
    }
}