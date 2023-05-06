package com.uleeankin.touristrouteselection.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeService {

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
        LocalTime start = TimeService.convert(startTime).toLocalTime();
        LocalTime end = TimeService.convert(endTime).toLocalTime();
        LocalTime timeBreak = TimeService.convert(breakTime).toLocalTime();
        LocalTime timeDuration = TimeService.convert(duration).toLocalTime();
        LocalTime eventStartTime;

        List<Time> sessions = new ArrayList<>();

        do {
            sessions.add(TimeService.convert(start.toString()));
            start = start.plusHours(timeBreak.getHour())
                    .plusHours(timeDuration.getHour())
                    .plusMinutes(timeBreak.getMinute())
                    .plusMinutes(timeDuration.getMinute());

            eventStartTime = start.plusHours(timeDuration.getHour()).plusMinutes(timeDuration.getMinute());

        } while (eventStartTime.isBefore(end));

        return sessions;
    }

    public static Time sumTime(Time first, Time second) {
        LocalTime firstTime = first.toLocalTime();
        LocalTime secondTime = second.toLocalTime();

        firstTime = firstTime.plusHours(secondTime.getHour());
        firstTime = firstTime.plusMinutes(secondTime.getMinute());

        return TimeService.convert(firstTime.toString());
    }
}
