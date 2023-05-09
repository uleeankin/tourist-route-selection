package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.utils.TimeService;

import java.sql.Time;
import java.util.*;

public class RouteFinder<T extends GraphNode> {

    private final Graph<T> graph;
    private final Scorer<T> scorer;

    private Time maxTime;

    private Double maxPrice;

    private Time startTime;

    public RouteFinder(Graph<T> graph, Scorer<T> scorer) {
        this.graph = graph;
        this.scorer = scorer;
    }

    public List<T> findRoute(T from, T to) throws IllegalStateException {

        Queue<RouteNode<T>> openSet = new PriorityQueue<>();
        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        Set<T> shortestPathFound = new HashSet<>();

        RouteNode<T> start = new RouteNode<>(
                from, null, 0d,
                this.scorer.getTime(from), this.scorer.getPrice(from),
                this.scorer.computeCost(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        while(!openSet.isEmpty()) {
            RouteNode<T> currentNode = openSet.poll();
            shortestPathFound.add(currentNode.getCurrent());

            if (currentNode.getCurrent().equals(to)) {
                return this.buildPath(currentNode, allNodes);
            }

            this.graph.getConnections(currentNode.getCurrent()).forEach(connection -> {

                if (!shortestPathFound.contains(connection)) {

                    RouteNode<T> nextNode = allNodes
                            .getOrDefault(connection, new RouteNode<>(connection));
                    allNodes.put(connection, nextNode);

                    double newDistanceScore = currentNode.getDistanceScore()
                            + scorer.computeCost(currentNode.getCurrent(), connection);

                    double newPriceScore = currentNode.getPriceScore()
                            + scorer.computePrice(currentNode.getCurrent(), connection);

                    Time newTimeScore = TimeService.sumTime(currentNode.getTimeScore(),
                            scorer.computeTime(currentNode.getCurrent(), connection));


                    if (this.scorer.isCompulsory(nextNode.getCurrent())) {

                        this.checkTypeAndAdd(to, openSet, currentNode, connection, nextNode,
                                newDistanceScore, newTimeScore, newPriceScore);

                    } else {
                        if (this.compareScore(newDistanceScore, newTimeScore,
                                newPriceScore, nextNode)) {
                            this.checkTypeAndAdd(to, openSet, currentNode, connection, nextNode,
                                    newDistanceScore, newTimeScore, newPriceScore);
                        }
                    }

                }

            });
        }

        throw new IllegalStateException("No route found");
    }

    private void checkTypeAndAdd(T to, Queue<RouteNode<T>> openSet,
                                 RouteNode<T> currentNode, T connection,
                                 RouteNode<T> nextNode, double distance,
                                 Time time, double price) {
        if (this.scorer.isEvent(nextNode.getCurrent())) {

            if (this.scorer.isRightTime(
                    this.scorer.computeTime(
                            currentNode.getCurrent(),
                            nextNode.getCurrent()),
                    this.scorer.getEventTime(nextNode.getCurrent()),
                    this.startTime)) {

                this.addNode(distance, time, price, currentNode,
                        connection, nextNode, to, openSet);

            }

        } else {
            this.addNode(distance, time, price, currentNode,
                    connection, nextNode, to, openSet);
        }
    }

    private void addNode(double distance, Time time, double price,
                         RouteNode<T> currentNode, T connection,
                         RouteNode<T> nextNode, T to,
                         Queue<RouteNode<T>> openSet) {

        nextNode.setPrevious(currentNode.getCurrent());
        nextNode.setDistanceScore(distance);
        nextNode.setTimeScore(time);
        nextNode.setPriceScore(price);
        nextNode.setEstimatedScore(distance
                + scorer.computeCost(connection, to));
        openSet.add(nextNode);
    }

    private boolean compareScore(double distanceScore, Time timeScore,
                                 double priceScore, RouteNode<T> nextNode) {

        boolean scoreComparison = true;

        if (hasTimeConstraint()) {
            scoreComparison = scoreComparison
                    && (timeScore.before(nextNode.getTimeScore()))
                    && (timeScore.before(this.maxTime));
        }

        if (hasPriceConstraint()) {
            scoreComparison = scoreComparison
                    && (priceScore < nextNode.getPriceScore())
                    && (priceScore <= this.maxPrice);
        }

        if (!hasTimeConstraint() && ! hasPriceConstraint()) {
            scoreComparison = scoreComparison
                    && (distanceScore < nextNode.getDistanceScore());
        }

        return scoreComparison;
    }

    private List<T> buildPath(RouteNode<T> currentNode, Map<T, RouteNode<T>> allNodes) {
        List<T> route = new ArrayList<>();
        do {
            route.add(0, currentNode.getCurrent());
            currentNode = allNodes.get(currentNode.getPrevious());
        } while (currentNode != null);
        return route;
    }

    public void addTimeConstraint(Time maxTime) {
        this.maxTime = maxTime;
    }

    public void addPriceConstraint(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setStartTime(Time routeStartTime) {
        this.startTime = routeStartTime;
    }

    private boolean hasTimeConstraint() {
        return this.maxTime != null;
    }

    private boolean hasPriceConstraint() {
        return this.maxPrice != null;
    }
}
