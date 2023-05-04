package com.uleeankin.touristrouteselection.algorithm;

import java.sql.Time;

public interface Scorer<T extends GraphNode> {

    double computeCost(T from, T to);

    Time computeTime(T from, T to);

    double computePrice(T from, T to);

    Time getTime(T current);

    double getPrice(T current);

}
