package com.uleeankin.touristrouteselection.algorithm;

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

                    double newDistanceScore = currentNode.getRouteScore()
                            + scorer.computeCost(currentNode.getCurrent(), connection);


                    if (this.scorer.isCompulsory(nextNode.getCurrent())) {

                        this.checkTypeAndAdd(to, openSet, currentNode, connection, nextNode, newDistanceScore);

                    } else {
                        if (this.compareScore(newDistanceScore, nextNode)) {
                            this.checkTypeAndAdd(to, openSet, currentNode, connection, nextNode, newDistanceScore);
                        }
                    }

                }

            });
        }

        throw new IllegalStateException("No route found");
    }

    private void checkTypeAndAdd(T to, Queue<RouteNode<T>> openSet,
                                 RouteNode<T> currentNode, T connection,
                                 RouteNode<T> nextNode, double newScore) {
        if (this.scorer.isEvent(nextNode.getCurrent())) {

            if (this.scorer.isRightTime(
                    this.scorer.computeTime(
                            currentNode.getCurrent(),
                            nextNode.getCurrent()),
                    this.scorer.getEventTime(nextNode.getCurrent()))) {

                this.addNode(newScore, currentNode, connection, nextNode, to, openSet);

            }

        } else {
            this.addNode(newScore, currentNode, connection, nextNode, to, openSet);
        }
    }

    private List<T> buildPath(RouteNode<T> currentNode, Map<T, RouteNode<T>> allNodes) {
        List<T> route = new ArrayList<>();
        do {
            route.add(0, currentNode.getCurrent());
            currentNode = allNodes.get(currentNode.getPrevious());
        } while (currentNode != null);
        return route;
    }

    private void addNode(double newScore, RouteNode<T> currentNode, T connection,
                         RouteNode<T> nextNode, T to,
                         Queue<RouteNode<T>> openSet) {

        nextNode.setPrevious(currentNode.getCurrent());
        nextNode.setRouteScore(newScore);
        nextNode.setEstimatedScore(newScore
                + scorer.computeCost(connection, to));
        openSet.add(nextNode);
    }

    private boolean compareScore(double newScore, RouteNode<T> nextNode) {

        return newScore < nextNode.getRouteScore();
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
