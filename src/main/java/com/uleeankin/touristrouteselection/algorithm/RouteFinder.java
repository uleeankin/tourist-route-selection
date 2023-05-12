package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.utils.TimeService;
import java.sql.Time;
import java.util.*;

public class RouteFinder {

    private static final int BASE_PRIORITY = 1;
    private static final int PRIORITY_UP = 1;
    private static final int COMPULSORY_PRIORITY_UP = 1;

    private final Graph graph;
    private final Scorer scorer;

    private Time maxTime;

    private Double maxPrice;

    private Time startTime;

    private boolean isPriceBound = false;
    private boolean isTimeBound = false;

    public RouteFinder(Graph graph, Scorer scorer) {
        this.graph = graph;
        this.scorer = scorer;
    }

    public List<PreliminaryActivity> findRoute(PreliminaryActivity from)
            throws IllegalStateException {

        Queue<NodeWrapper> queue = new PriorityQueue<>();
        Map<PreliminaryActivity, NodeWrapper> allNodes = new HashMap<>();
        Set<PreliminaryActivity> shortestPathFound = new HashSet<>();

        NodeWrapper start = new NodeWrapper(
                from, null, 0d,
                from.getActivity().getDuration(),
                from.getActivity().getPrice(),
                hasPriceConstraint(), hasTimeConstraint());
        start.setPriority(BASE_PRIORITY);
        queue.add(start);
        allNodes.put(from, start);

        while(!queue.isEmpty()) {

            NodeWrapper currentNode = queue.poll();
            queue.clear();
            shortestPathFound.add(currentNode.getCurrent());

            this.graph.getConnections(currentNode.getCurrent()).forEach(connection -> {

                if (!shortestPathFound.contains(connection)) {

                    NodeWrapper neighborWrapper = allNodes
                            .getOrDefault(connection, new NodeWrapper(connection,
                                    hasPriceConstraint(), hasTimeConstraint()));
                    allNodes.put(connection, neighborWrapper);

                    neighborWrapper.setPriority(BASE_PRIORITY);

                    double newDistanceScore = currentNode.getDistanceScore()
                            + scorer.computeCost(currentNode.getCurrent(), connection);

                    double newPriceScore = currentNode.getPriceScore()
                            + scorer.computePrice(currentNode.getCurrent(), connection);

                    Time newTimeScore = TimeService.sumTime(currentNode.getTimeScore(),
                            scorer.computeTime(currentNode.getCurrent(), connection));

                    if (neighborWrapper.getCurrent().isCompulsory()) {
                        this.upPriority(neighborWrapper, COMPULSORY_PRIORITY_UP);
                        this.checkTypeAndAdd(queue, currentNode, neighborWrapper,
                                newDistanceScore, newTimeScore, newPriceScore);

                    } else {
                        if (this.compareScore(newTimeScore, newPriceScore)) {
                            this.checkTypeAndAdd(queue, currentNode, neighborWrapper,
                                    newDistanceScore, newTimeScore, newPriceScore);
                        }
                    }
                }

            });

            if (queue.size() == 0
                || this.isPriceBound
                || this.isTimeBound) {
                return this.buildPath(currentNode, allNodes);
            }
        }

        throw new IllegalStateException("No route found");
    }

    private void checkTypeAndAdd(Queue<NodeWrapper> openSet, NodeWrapper currentNode,
                                 NodeWrapper neighborWrapper, double distance,
                                 Time time, double price) {
        if (neighborWrapper.getCurrent().isEvent()) {
            if (this.scorer.isRightTime(
                    this.scorer.computeTime(
                            currentNode.getCurrent(),
                            neighborWrapper.getCurrent()),
                    neighborWrapper.getCurrent().getEventTime(),
                    this.startTime)) {

                this.upPriority(neighborWrapper, PRIORITY_UP);
                this.addNode(distance, time, price,
                        currentNode, neighborWrapper, openSet);

            }

        } else {
            this.addNode(distance, time, price,
                    currentNode, neighborWrapper, openSet);
        }
    }

    private void addNode(double distance, Time time, double price,
                         NodeWrapper currentNode, NodeWrapper neighborWrapper,
                         Queue<NodeWrapper> openSet) {

        neighborWrapper.setPrevious(currentNode.getCurrent());
        neighborWrapper.setDistanceScore(distance);
        neighborWrapper.setTimeScore(time);
        neighborWrapper.setPriceScore(price);
        openSet.remove(neighborWrapper);
        openSet.add(neighborWrapper);
    }

    private boolean compareScore(Time timeScore, double priceScore) {

        boolean scoreComparison = true;

        if (hasTimeConstraint()) {
            scoreComparison = scoreComparison
                    && (timeScore.before(this.maxTime));

            this.isTimeBound = this.isTimeBound && (timeScore.after(this.maxTime));
        }

        if (hasPriceConstraint()) {
            scoreComparison = scoreComparison
                    && (priceScore <= this.maxPrice);

            this.isPriceBound = this.isPriceBound && (priceScore > this.maxPrice);
        }

        return scoreComparison;
    }

    private List<PreliminaryActivity> buildPath(NodeWrapper currentNode,
                                                Map<PreliminaryActivity, NodeWrapper> allNodes) {
        List<PreliminaryActivity> route = new ArrayList<>();
        do {
            route.add(currentNode.getCurrent());
            currentNode = allNodes.get(currentNode.getPrevious());
        } while (currentNode != null);
        Collections.reverse(route);
        return route;
    }

    private void upPriority(NodeWrapper node, int priorityUp) {
        node.setPriority(node.getPriority() + priorityUp);
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
