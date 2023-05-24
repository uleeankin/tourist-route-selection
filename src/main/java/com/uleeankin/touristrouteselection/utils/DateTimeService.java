package com.uleeankin.touristrouteselection.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeService {

    public static Time convert(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            return new Time(dateFormat.parse(time).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Time convert(double time) {
        int hours = (int) time;
        int minutes = (int) (60 * (time - hours));
        return DateTimeService.convert(String.format("%d:%d", hours, minutes));
    }

    public static List<Time> getSessions(String startTime, String endTime,
                                         String breakTime, String duration) {
        LocalTime start = DateTimeService.convert(startTime).toLocalTime();
        LocalTime end = DateTimeService.convert(endTime).toLocalTime();
        LocalTime timeBreak = DateTimeService.convert(breakTime).toLocalTime();
        LocalTime timeDuration = DateTimeService.convert(duration).toLocalTime();
        LocalTime eventStartTime;

        List<Time> sessions = new ArrayList<>();

        do {
            sessions.add(DateTimeService.convert(start.toString()));
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

        return DateTimeService.convert(firstTime.toString());
    }

    public static Date subTime(Time first, Time second) {
        LocalTime firstTime = first.toLocalTime();
        LocalTime secondTime = second.toLocalTime();

        firstTime = firstTime.minusHours(secondTime.getHour());
        firstTime = firstTime.minusMinutes(secondTime.getMinute());

        return DateTimeService.convert(firstTime.toString());
    }

    public static List<java.sql.Date> getDates(java.sql.Date startDate, java.sql.Date endDate) {
        List<java.sql.Date> dates = new ArrayList<>();
        LocalDate start = startDate.before(
                new java.sql.Date(new java.util.Date().getTime())) ?
                startDate.toLocalDate()
                : new java.sql.Date(new java.util.Date().getTime()).toLocalDate();
        LocalDate end = endDate.toLocalDate();
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1))
        {
            dates.add(java.sql.Date.valueOf(date.toString()));
        }
        return dates;
    }
}
