package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Comparator;

@Getter
@Setter
public class NodeWrapper implements Comparable<NodeWrapper> {

    private final PreliminaryActivity current;
    private PreliminaryActivity previous;

    private double distanceScore;

    private Time timeScore;

    private double priceScore;

    private int priority;

    public NodeWrapper(PreliminaryActivity current) {
        this(current, null,
                Double.POSITIVE_INFINITY,
                new Time(Long.MAX_VALUE),
                Double.POSITIVE_INFINITY);
    }

    public NodeWrapper(PreliminaryActivity current, PreliminaryActivity previous,
                       double distanceScore, Time timeScore,
                       double priceScore) {
        this.current = current;
        this.previous = previous;
        this.distanceScore = distanceScore;
        this.timeScore = timeScore;
        this.priceScore = priceScore;
    }

    @Override
    public int compareTo(NodeWrapper other) {
        //return Comparator.comparing((NodeWrapper x) -> x.priority).reversed().compare(this, other);
        return Double.compare(this.distanceScore, other.distanceScore);
    }

}
