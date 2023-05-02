package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.models.activity.Activity;

import java.util.*;

public class RouteCreator {

    public RouteCreator() {
    }

    public List<Activity> createNewRoute(List<Activity> routeActivities) {
        List<Activity> sortedList = this.sort(routeActivities);
        Set<Activity> activities = new HashSet<>(sortedList);
        Map<Long, Set<Long>> connections = getConnections(sortedList);

        Graph<Activity> graph = new Graph<>(activities, connections);
        RouteFinder<Activity> finder = new RouteFinder<>(
                graph, new ActivityScorer(), new ActivityScorer());

        Activity firstPlace = sortedList.get(0);
        Activity lastPlace = sortedList.get(sortedList.size() - 1);

        List<Activity> route = finder.findRoute(firstPlace, lastPlace);

        return route;
    }

    private Map<Long, Set<Long>> getConnections(List<Activity> routeActivities) {
        Map<Long, Set<Long>> connections = new HashMap<>();
        List<Long> connectionList;

        for (long i = 0; i < routeActivities.size(); i++) {
            connectionList = new ArrayList<>();
            for (long j = 0; j < routeActivities.size(); j++) {
                if (j != i) {
                    connectionList.add(routeActivities.get((int) j).getId());
                }
            }
            connections.put(routeActivities.get((int) i).getId(), new HashSet<>(connectionList));
        }

        return connections;
    }

    private List<Activity> sort(List<Activity> routeActivities) {

        routeActivities.sort(new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                int latitudeCompare = Double.compare(
                        o1.getCoordinate().getLatitude(),
                        o2.getCoordinate().getLatitude());

                if (latitudeCompare != 0) {
                    return latitudeCompare;
                }

                return Double.compare(o1.getCoordinate().getLongitude(),
                        o2.getCoordinate().getLongitude());
            }
        });

        return routeActivities;
    }
}
