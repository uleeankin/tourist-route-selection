package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.sql.Time;

public interface Scorer {

    double computeCost(PreliminaryActivity from,
                       PreliminaryActivity to);

    Time computeTime(PreliminaryActivity from,
                     PreliminaryActivity to);

    double computePrice(PreliminaryActivity from, PreliminaryActivity to);


    boolean isRightTime(Time currentTime, Time eventStartTime, Time routeStartTime);

}
