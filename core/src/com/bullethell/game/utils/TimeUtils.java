package com.bullethell.game.utils;

public class TimeUtils {
    public static long convertToSeconds(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return 0;
        }

        String[] parts = timeStr.split(":");

        long seconds = 0;

        try {
            if (parts.length == 1) {
                // Only seconds are provided
                seconds = Long.parseLong(parts[0]);
            } else if (parts.length == 2) {
                // Minutes and seconds are provided
                long minutes = Long.parseLong(parts[0]);
                seconds = Long.parseLong(parts[1]);

                seconds = minutes * 60 + seconds;
            } else {
                throw new IllegalArgumentException("Invalid time format");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time string", e);
        }

        return seconds;
    }
}
