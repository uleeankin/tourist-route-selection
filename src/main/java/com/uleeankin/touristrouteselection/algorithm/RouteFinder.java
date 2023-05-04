package com.uleeankin.touristrouteselection.algorithm;

import java.sql.Time;
import java.util.*;

public class RouteFinder<T extends GraphNode> {

    private final Graph<T> graph;
    private final Scorer<T> scorer;

    private Time maxTime;

    private Double maxPrice;

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

                    double newScore = currentNode.getRouteScore()
                            + scorer.computeCost(currentNode.getCurrent(), connection);

                    if (newScore < nextNode.getRouteScore()) {
                        nextNode.setPrevious(currentNode.getCurrent());
                        nextNode.setRouteScore(newScore);
                        nextNode.setEstimatedScore(newScore
                                + scorer.computeCost(connection, to));
                        openSet.add(nextNode);
                    }

                }

            });
        }

        throw new IllegalStateException("No route found");
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
}
