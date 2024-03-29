package com.uleeankin.touristrouteselection;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.algorithm.RouteCreator;
import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.route.model.CreatedRoute;
import com.uleeankin.touristrouteselection.utils.DateTimeService;
import com.uleeankin.touristrouteselection.utils.json.JSONConverter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonConverterTests {

    @Test
    public void testJsonConverter() {
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(1L, "Памятник Евпатию Коловрату", "",
                        new Coordinate(1L, 54.6295, 39.7419,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, DateTimeService.convert("00:45")));

        activities.add(new Activity(2L, "Музей истории Воздушно-десантных войск", "",
                        new Coordinate(1L, 54.6330, 39.7362,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 350.0, DateTimeService.convert("01:20")));

        activities.add(new Activity(3L, "Рязанский государственный областной художественный музей имени И. П. Пожалостина",
                        "", new Coordinate(1L, 54.6272, 39.7517,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 300.0, DateTimeService.convert("01:45")));

        activities.add(new Activity(4L, "Мемориальный музей-усадьба академика И.П. Павлова", "",
                        new Coordinate(1L, 54.6317, 39.7271,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 400.0, DateTimeService.convert("01:30")));

        activities.add(new Activity(5L, "Музей Галерея пряников", "",
                        new Coordinate(1L, 54.6338, 39.7609,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 600.0, DateTimeService.convert("01:00")));

        activities.add(new Activity(6L, "Церковь Иоанна Кронштадтского", "",
                        new Coordinate(1L, 54.6104, 39.7117,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, DateTimeService.convert("00:30")));

        System.out.println(new JSONConverter().getCoordinatesJSON(activities));
    }

    @Test
    public void routeBuildingTest() {
        List<PreliminaryActivity> activities = new ArrayList<>();

        activities.add(new PreliminaryActivity("1",
                new Activity(1L, "Памятник Евпатию Коловрату", "", new Coordinate(1L, 54.6295, 39.7419,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, DateTimeService.convert("00:45")), false, null));

        activities.add(new PreliminaryActivity("1",
                new Activity(2L, "Музей истории Воздушно-десантных войск", "", new Coordinate(1L, 54.6330, 39.7362,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 350.0, DateTimeService.convert("01:20")), false, null));

        activities.add(new PreliminaryActivity("1",
                new Activity(3L, "Рязанский государственный областной художественный музей имени И. П. Пожалостина",
                        "", new Coordinate(1L, 54.6272, 39.7517,
                        new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 300.0, DateTimeService.convert("01:45")), false, null));

        activities.add(new PreliminaryActivity("1",
                new Activity(4L, "Мемориальный музей-усадьба академика И.П. Павлова", "",
                        new Coordinate(1L, 54.6317, 39.7271,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 400.0, DateTimeService.convert("01:30")), false, null));

        activities.add(new PreliminaryActivity("1",
                new Activity(5L, "Музей Галерея пряников", "",
                        new Coordinate(1L, 54.6338, 39.7609,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 600.0, DateTimeService.convert("01:00")),false, null));

        activities.add(new PreliminaryActivity("1",
                new Activity(6L, "Церковь Иоанна Кронштадтского", "",
                        new Coordinate(1L, 54.6104, 39.7117,
                                new City(1L, "")),
                        new Category(1L, ""),
                        new byte[]{0}, 0.0, DateTimeService.convert("00:30")), false, null));

        CreatedRoute route = new RouteCreator().createNewRoute(activities, null, "", "");

        List<Activity> result = new ArrayList<>();

        List<PreliminaryActivity> preliminaryActivities = route.getActivities();

        for (PreliminaryActivity a : preliminaryActivities) {
            result.add(a.getActivity());
        }

        System.out.println(new JSONConverter().getCoordinatesJSON(result));
    }
}
