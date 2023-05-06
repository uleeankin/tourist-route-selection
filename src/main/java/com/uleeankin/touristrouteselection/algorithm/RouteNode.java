package com.uleeankin.touristrouteselection.algorithm;

import java.sql.Time;

public class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {

    private final T current;
    private T previous;

    private double distanceScore;

    private Time timeScore;

    private double priceScore;

    private double estimatedScore;

    public RouteNode(T current) {
        this(current, null,
                Double.POSITIVE_INFINITY,
                new Time(Long.MAX_VALUE),
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY);
    }

    public RouteNode(T current, T previous,
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

    public T getCurrent() {
        return current;
    }

    public T getPrevious() {
        return previous;
    }

    public void setPrevious(T previous) {
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
