package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.utils.TimeService;

import java.sql.Time;

public class ActivityScorer implements Scorer{

    private static final double R = 6371.0;

    private static final double V = 4.0;

    @Override
    public double computeCost(PreliminaryActivity from
            , PreliminaryActivity to) {
        double firstNodeLatitude = Math.toRadians(from.getActivity().getCoordinate().getLatitude());
        double firstNodeLongitude = Math.toRadians(from.getActivity().getCoordinate().getLongitude());
        double secondNodeLatitude = Math.toRadians(to.getActivity().getCoordinate().getLatitude());
        double secondNodeLongitude = Math.toRadians(to.getActivity().getCoordinate().getLongitude());
        return 2 * R * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(
                                secondNodeLatitude - firstNodeLatitude), 2)
                                + Math.cos(firstNodeLatitude)
                                * Math.cos(secondNodeLatitude)
                                * (Math.pow(Math.sin(
                                secondNodeLongitude - firstNodeLongitude), 2))
                ));
    }

    @Override
    public Time computeTime(PreliminaryActivity from,
                            PreliminaryActivity to) {

        return TimeService.sumTime(TimeService.convert(
                computeCost(from, to) / V),
                to.getActivity().getDuration());
    }

    @Override
    public double computePrice(PreliminaryActivity from,
                               PreliminaryActivity to) {
        return to.getActivity().getPrice();
    }

    @Override
    public Time getTime(PreliminaryActivity current) {
        return current.getActivity().getDuration();
    }

    @Override
    public double getPrice(PreliminaryActivity current) {
        return current.getActivity().getPrice();
    }

    @Override
    public boolean isCompulsory(PreliminaryActivity current) {
        return current.isCompulsory();
    }

    @Override
    public boolean isEvent(PreliminaryActivity current) {
        return current.isEvent();
    }

    @Override
    public boolean isRightTime(Time currentTime, Time eventStartTime, Time routeStartTime) {
        return TimeService.sumTime(routeStartTime, currentTime).before(eventStartTime);
    }

    @Override
    public Time getEventTime(PreliminaryActivity current) {
        return current.getEventTime();
    }
}
