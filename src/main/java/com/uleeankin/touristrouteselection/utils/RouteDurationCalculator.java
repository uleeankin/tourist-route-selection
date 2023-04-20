package com.uleeankin.touristrouteselection.utils;

import com.uleeankin.touristrouteselection.algorithm.ActivityScorer;
import com.uleeankin.touristrouteselection.models.activity.Activity;

import java.util.List;

public class RouteDurationCalculator {

    private final  ActivityScorer activityScorer;

    public RouteDurationCalculator() {
        this.activityScorer = new ActivityScorer();
    }

    public double getDuration(List<Activity> activities) {
        double duration = 0d;
        if (activities.size() > 1) {
            for (int i = 1; i < activities.size(); i++) {
                duration += activityScorer.computeCost(activities.get(i - 1), activities.get(i));
            }
        }
        return duration;
    }

}
