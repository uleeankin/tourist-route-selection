package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import lombok.Getter;
import lombok.NonNull;
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

    private final boolean hasTimeComparing;

    private final boolean hasPriceComparing;

    public NodeWrapper(PreliminaryActivity current,
                       boolean hasPriceComparing, boolean hasTimeComparing) {
        this(current, null,
                Double.POSITIVE_INFINITY,
                new Time(Long.MAX_VALUE),
                Double.POSITIVE_INFINITY,
                hasPriceComparing, hasTimeComparing);
    }

    public NodeWrapper(PreliminaryActivity current, PreliminaryActivity previous,
                       double distanceScore, Time timeScore,
                       double priceScore,
                       boolean hasPriceComparing, boolean hasTimeComparing) {
        this.current = current;
        this.previous = previous;
        this.distanceScore = distanceScore;
        this.timeScore = timeScore;
        this.priceScore = priceScore;
        this.hasPriceComparing = hasPriceComparing;
        this.hasTimeComparing = hasTimeComparing;
    }

    @Override
    public int compareTo(@NonNull NodeWrapper other) {
        Comparator<NodeWrapper> compare =
                Comparator.comparing(NodeWrapper::getPriority).reversed();

        if (this.hasTimeComparing) {
            compare = compare.thenComparing(NodeWrapper::getTimeScore);
        }

        if (this.hasPriceComparing) {
            compare = compare.thenComparing(NodeWrapper::getPriceScore);
        }

        return compare.thenComparing(NodeWrapper::getDistanceScore)
                    .compare(this, other);
    }

}
