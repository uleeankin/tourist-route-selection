package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.sql.Time;

public interface Scorer {

    double computeCost(PreliminaryActivity from,
                       PreliminaryActivity to);

    Time computeTime(PreliminaryActivity from,
                     PreliminaryActivity to);

    double computePrice(PreliminaryActivity from, PreliminaryActivity to);

    Time getTime(PreliminaryActivity current);

    double getPrice(PreliminaryActivity current);

    boolean isCompulsory(PreliminaryActivity current);

    boolean isEvent(PreliminaryActivity current);

    boolean isRightTime(Time currentTime, Time eventStartTime, Time routeStartTime);

    Time getEventTime(PreliminaryActivity current);

}
