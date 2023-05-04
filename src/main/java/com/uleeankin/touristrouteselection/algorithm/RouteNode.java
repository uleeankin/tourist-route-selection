package com.uleeankin.touristrouteselection.algorithm;

import java.sql.Time;

public class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {

    private final T current;
    private T previous;

    private double routeScore;

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
                     double routeScore, Time timeScore,
                     double priceScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
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

    public double getRouteScore() {
        return routeScore;
    }

    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }
}
