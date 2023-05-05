package com.uleeankin.touristrouteselection.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ToTimeConverter {

    public static Time convert(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            return new Time(dateFormat.parse(time).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Time convert(double time) {
        return new Time((long) time * 24 * 60 * 60 * 1000);
    }

    public static List<Time> getSessions(String startTime, String endTime,
                                         String breakTime, String duration) {
        LocalTime start = ToTimeConverter.convert(startTime).toLocalTime();
        LocalTime end = ToTimeConverter.convert(endTime).toLocalTime();
        LocalTime timeBreak = ToTimeConverter.convert(breakTime).toLocalTime();
        LocalTime timeDuration = ToTimeConverter.convert(duration).toLocalTime();
        LocalTime eventStartTime;

        List<Time> sessions = new ArrayList<>();

        do {
            sessions.add(ToTimeConverter.convert(start.toString()));
            start = start.plusHours(timeBreak.getHour())
                    .plusHours(timeDuration.getHour())
                    .plusMinutes(timeBreak.getMinute())
                    .plusMinutes(timeDuration.getMinute());

            eventStartTime = start.plusHours(timeDuration.getHour()).plusMinutes(timeDuration.getMinute());

        } while (eventStartTime.isBefore(end));

        return sessions;
    }
}
