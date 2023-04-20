package com.uleeankin.touristrouteselection.algorithm;

public interface Scorer<T extends GraphNode> {

    double computeCost(T from, T to);

}
