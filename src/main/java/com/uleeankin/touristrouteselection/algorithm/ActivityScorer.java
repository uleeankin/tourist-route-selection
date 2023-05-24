package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.utils.DateTimeService;

import java.sql.Time;

public class ActivityScorer implements Scorer{

    private static final Time TIME_DELTA = DateTimeService.convert("01:00");

    private static final double R = 6371.0;

    private static final double V = 4.0;

    @Override
    public double computeCost(
            PreliminaryActivity from, PreliminaryActivity to) {
        double firstNodeLatitude = Math.toRadians(from.getActivity().getCoordinate().getLatitude());
        double firstNodeLongitude = Math.toRadians(from.getActivity().getCoordinate().getLongitude());
        double secondNodeLatitude = Math.toRadians(to.getActivity().getCoordinate().getLatitude());
        double secondNodeLongitude = Math.toRadians(to.getActivity().getCoordinate().getLongitude());
        return 2 * R * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(
                                (secondNodeLatitude - firstNodeLatitude) / 2.0), 2)
                                + Math.cos(firstNodeLatitude)
                                * Math.cos(secondNodeLatitude)
                                * (Math.pow(Math.sin(
                                (secondNodeLongitude - firstNodeLongitude) / 2.0), 2))
                ));
    }

    @Override
    public Time computeTime(PreliminaryActivity from,
                            PreliminaryActivity to) {

        return DateTimeService.sumTime(DateTimeService.convert(
                computeCost(from, to) / V),
                to.getActivity().getDuration());
    }

    @Override
    public double computePrice(PreliminaryActivity from,
                               PreliminaryActivity to) {
        return to.getActivity().getPrice();
    }


    @Override
    public boolean isRightTime(Time currentTime, Time eventStartTime, Time routeStartTime) {
        return DateTimeService.sumTime(routeStartTime, currentTime)
                .before(eventStartTime)
                && DateTimeService.sumTime(routeStartTime, currentTime)
                .after(DateTimeService.subTime(eventStartTime, TIME_DELTA));
    }

}
