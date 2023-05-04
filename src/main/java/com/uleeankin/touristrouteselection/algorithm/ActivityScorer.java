package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;

import java.sql.Time;

public class ActivityScorer implements Scorer<PreliminaryRouteActivity> {

    private static final double R = 6371.0;

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

    //todo override methods

    @Override
    public Time computeTime(PreliminaryRouteActivity from,
                            PreliminaryRouteActivity to) {
        return null;
    }

    @Override
    public double computePrice(PreliminaryRouteActivity from,
                               PreliminaryRouteActivity to) {
        return 0;
    }

    @Override
    public Time getTime(PreliminaryRouteActivity current) {
        return current.getActivity().getDuration();
    }

    @Override
    public double getPrice(PreliminaryRouteActivity current) {
        return current.getActivity().getPrice();
    }
}
