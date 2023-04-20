package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.models.activity.Activity;

public class ActivityScorer implements Scorer<Activity> {

    private static final double R = 6371.0;

    @Override
    public double computeCost(Activity from, Activity to) {
        double firstNodeLatitude = Math.toRadians(from.getCoordinate().getLatitude());
        double firstNodeLongitude = Math.toRadians(from.getCoordinate().getLongitude());
        double secondNodeLatitude = Math.toRadians(to.getCoordinate().getLatitude());
        double secondNodeLongitude = Math.toRadians(to.getCoordinate().getLongitude());
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
}
