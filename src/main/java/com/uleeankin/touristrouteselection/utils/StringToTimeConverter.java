package com.uleeankin.touristrouteselection.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringToTimeConverter {

    public static Time convert(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            return new Time(dateFormat.parse(time).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
