package com.uleeankin.touristrouteselection.utils;

import com.uleeankin.touristrouteselection.models.activity.Activity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class RouteTimeCalculator {

    public RouteTimeCalculator() {
    }

    public Time getTime(List<Activity> activities) {

        LocalTime localTime = LocalTime.parse("00:00:00");

        for (Activity activity : activities) {
            LocalTime activityTime = activity.getDuration().toLocalTime();
            localTime = localTime.plusHours(activityTime.getHour());
            localTime = localTime.plusMinutes(activityTime.getMinute());
            localTime = localTime.plusSeconds(activityTime.getSecond());
        }

        return StringToTimeConverter.convert(localTime.toString());
    }
}
