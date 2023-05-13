package com.uleeankin.touristrouteselection.algorithm;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.route.model.CreatedRoute;
import com.uleeankin.touristrouteselection.utils.TimeService;
import org.springframework.data.util.Pair;

import java.sql.Time;
import java.util.*;

public class RouteCreator {

    public RouteCreator() {
    }

    public CreatedRoute createNewRoute(
            List<PreliminaryActivity> routeActivities,
            Double maxPrice, String maxTime, String startTime) {
        List<PreliminaryActivity> sortedList = this.sort(routeActivities);
        Set<PreliminaryActivity> activities = new HashSet<>(sortedList);
        Map<Long, Set<Long>> connections = getConnections(sortedList);

        Graph graph = new Graph(activities, connections);
        RouteFinder finder = new RouteFinder(
                graph, new ActivityScorer());

        if (maxPrice != null) {
            finder.addPriceConstraint(maxPrice);
        }

        if (!maxTime.equals("")) {
            finder.addTimeConstraint(TimeService.convert(maxTime));
        }

        if (!startTime.equals("")) {
            finder.setStartTime(TimeService.convert(startTime));
        }

        return new CreatedRoute(
                finder.findRoute(sortedList.get(sortedList.size() - 1)),
                finder.getPathTime(), finder.getPathPrice(), finder.getPathDuration());
    }

    private Map<Long, Set<Long>> getConnections(List<PreliminaryActivity> routeActivities) {
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

    private List<PreliminaryActivity> sort(List<PreliminaryActivity> routeActivities) {

        routeActivities.sort(new Comparator<PreliminaryActivity>() {
            @Override
            public int compare(PreliminaryActivity o1, PreliminaryActivity o2) {
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
