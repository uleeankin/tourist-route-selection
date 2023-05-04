package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;

import java.util.*;

public class RouteCreator {

    public RouteCreator() {
    }

    public List<PreliminaryRouteActivity> createNewRoute(List<PreliminaryRouteActivity> routeActivities) {
        List<PreliminaryRouteActivity> sortedList = this.sort(routeActivities);
        Set<PreliminaryRouteActivity> activities = new HashSet<>(sortedList);
        Map<Long, Set<Long>> connections = getConnections(sortedList);

        Graph<PreliminaryRouteActivity> graph = new Graph<>(activities, connections);
        RouteFinder<PreliminaryRouteActivity> finder = new RouteFinder<>(
                graph, new ActivityScorer());

        return finder.findRoute(sortedList.get(0),
                sortedList.get(sortedList.size() - 1));
    }

    private Map<Long, Set<Long>> getConnections(List<PreliminaryRouteActivity> routeActivities) {
        Map<Long, Set<Long>> connections = new HashMap<>();
        List<Long> connectionList;

        for (long i = 0; i < routeActivities.size(); i++) {
            connectionList = new ArrayList<>();
            for (long j = 0; j < routeActivities.size(); j++) {
                if (j != i) {
                    connectionList.add(routeActivities.get((int) j).getActivity().getId());
                }
            }
            connections.put(
                    routeActivities.get((int) i).getActivity().getId(),
                    new HashSet<>(connectionList));
        }

        return connections;
    }

    private List<PreliminaryRouteActivity> sort(List<PreliminaryRouteActivity> routeActivities) {

        routeActivities.sort(new Comparator<PreliminaryRouteActivity>() {
            @Override
            public int compare(PreliminaryRouteActivity o1, PreliminaryRouteActivity o2) {
                int latitudeCompare = Double.compare(
                        o1.getActivity().getCoordinate().getLatitude(),
                        o2.getActivity().getCoordinate().getLatitude());

                if (latitudeCompare != 0) {
                    return latitudeCompare;
                }

                return Double.compare(o1.getActivity().getCoordinate().getLongitude(),
                        o2.getActivity().getCoordinate().getLongitude());
            }
        });

        return routeActivities;
    }
}
