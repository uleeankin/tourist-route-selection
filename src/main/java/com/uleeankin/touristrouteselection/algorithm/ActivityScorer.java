package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.utils.TimeService;

import java.sql.Time;

public class ActivityScorer implements Scorer<PreliminaryRouteActivity> {

    private static final double R = 6371.0;

    private static final double V = 4.0;

    @Override
    public double computeCost(PreliminaryRouteActivity from
            , PreliminaryRouteActivity to) {
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
    public Time computeTime(PreliminaryRouteActivity from,
                            PreliminaryRouteActivity to) {

        return TimeService.sumTime(TimeService.convert(
                computeCost(from, to) / V),
                to.getActivity().getDuration());
    }

    @Override
    public double computePrice(PreliminaryRouteActivity from,
                               PreliminaryRouteActivity to) {
        return to.getActivity().getPrice();
    }

    @Override
    public Time getTime(PreliminaryRouteActivity current) {
        return current.getActivity().getDuration();
    }

    @Override
    public double getPrice(PreliminaryRouteActivity current) {
        return current.getActivity().getPrice();
    }

    @Override
    public boolean isCompulsory(PreliminaryRouteActivity current) {
        return current.isCompulsory();
    }

    @Override
    public boolean isEvent(PreliminaryRouteActivity current) {
        return current.isEvent();
    }

    @Override
    public boolean isRightTime(Time currentTime, Time eventStartTime, Time routeStartTime) {
        return TimeService.sumTime(routeStartTime, currentTime).before(eventStartTime);
    }

    @Override
    public Time getEventTime(PreliminaryRouteActivity current) {
        return current.getEventTime();
    }
}
