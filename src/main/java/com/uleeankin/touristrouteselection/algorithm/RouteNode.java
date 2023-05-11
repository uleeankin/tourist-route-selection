package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;

import java.sql.Time;

public class RouteNode implements Comparable<RouteNode> {

    private final PreliminaryActivity current;
    private PreliminaryActivity previous;

    private double distanceScore;

    private Time timeScore;

    private double priceScore;

    private double estimatedScore;

    public RouteNode(PreliminaryActivity current) {
        this(current, null,
                Double.POSITIVE_INFINITY,
                new Time(Long.MAX_VALUE),
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY);
    }

    public RouteNode(PreliminaryActivity current, PreliminaryActivity previous,
                     double distanceScore, Time timeScore,
                     double priceScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.distanceScore = distanceScore;
        this.timeScore = timeScore;
        this.priceScore = priceScore;
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(RouteNode other) {
        if (this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if (this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }

    public PreliminaryActivity getCurrent() {
        return current;
    }

    public PreliminaryActivity getPrevious() {
        return previous;
    }

    public void setPrevious(PreliminaryActivity previous) {
        this.previous = previous;
    }

    public double getDistanceScore() {
        return distanceScore;
    }

    public void setDistanceScore(double routeScore) {
        this.distanceScore = routeScore;
    }

    public void setTimeScore(Time timeScore) {
        this.timeScore = timeScore;
    }

    public void setPriceScore(double priceScore) {
        this.priceScore = priceScore;
    }

    public Time getTimeScore() {
        return timeScore;
    }

    public double getPriceScore() {
        return priceScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }

}
