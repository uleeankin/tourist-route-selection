package com.uleeankin.touristrouteselection;


import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.algorithm.RouteCreator;
import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.utils.TimeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgorithmTests {

    @Test
    public void createWithoutEvents() {
        List<PreliminaryRouteActivity> activities = new ArrayList<>();

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(10L, "", "", new Coordinate(1L, 54.6295, 39.7419,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, TimeService.convert("00:45")),
                true, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(11L, "", "", new Coordinate(1L, 54.6330, 39.7362,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 350.0, TimeService.convert("01:20")),
                true, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(12L, "", "", new Coordinate(1L, 54.6272, 39.7517,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 300.0, TimeService.convert("01:45")),
                true, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(13L, "", "", new Coordinate(1L, 54.6317, 39.7271,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 400.0, TimeService.convert("01:30")),
                false, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(14L, "", "", new Coordinate(1L, 54.0846, 61.5366,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, TimeService.convert("01:50")),
                false, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(15L, "", "", new Coordinate(1L, 54.6338, 39.7609,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 600.0, TimeService.convert("01:00")),
                false, false, null));

        activities.add(new PreliminaryRouteActivity("1",
                new Activity(16L, "", "", new Coordinate(1L, 54.6104, 39.7117,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, TimeService.convert("00:30")),
                false, false, null));

        List<PreliminaryRouteActivity> creator = new RouteCreator().createNewRoute(activities, null, null, "");

        //creator.forEach(x -> System.out.println(x.getActivity().getId()));

        //this.sort(activities).forEach(x -> System.out.println(x.getActivity().getId()));
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