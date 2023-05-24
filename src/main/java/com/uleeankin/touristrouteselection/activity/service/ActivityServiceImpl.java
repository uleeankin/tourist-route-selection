package com.uleeankin.touristrouteselection.activity.service;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.service.PreliminaryActivityService;
import com.uleeankin.touristrouteselection.activity.model.ActivityStatus;
import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import com.uleeankin.touristrouteselection.activity.repository.ActivityRepository;
import com.uleeankin.touristrouteselection.activity.attributes.category.repository.CategoryRepository;
import com.uleeankin.touristrouteselection.city.repository.CityRepository;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.repository.CoordinateRepository;
import com.uleeankin.touristrouteselection.utils.DateTimeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final CoordinateRepository coordinateRepository;

    private final CityRepository cityRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository,
                               CoordinateRepository coordinateRepository,
                               CityRepository cityRepository,
                               CategoryRepository categoryRepository) {
        this.activityRepository = activityRepository;
        this.coordinateRepository = coordinateRepository;
        this.cityRepository = cityRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addActivity(String name, String description, String cityName,
                            String categoryName, Double latitude, Double longitude,
                            String time, Double price, byte[] photo) {


        Optional<City> city = this.cityRepository.findByName(cityName);
        Optional<Category> category = this.categoryRepository.findByName(categoryName);

        if (city.isPresent() && category.isPresent()) {
            this.coordinateRepository.addCoordinate(latitude, longitude, city.get().getId());
            Optional<Coordinate> coordinate = this.coordinateRepository.getCoordinate(latitude, longitude);

            if (coordinate.isPresent()) {
                Time sqlTime = DateTimeService.convert(time);
                //TODO: добавить фото
                this.activityRepository.addActivity(name, description,
                        coordinate.get().getId(), category.get().getId(), photo, sqlTime, price);
            }
        }
    }

    @Override
    public List<Activity> getByCity(String cityName) {
        return this.activityRepository.findByCity(cityName);
    }

    @Override
    public List<Activity> getByCityAndCategory(String city, String category) {
        return this.activityRepository.findByCityAndCategory(city, category);
    }

    @Override
    public void addToFavourites(String userId, Long activityId) {
        this.activityRepository.addToFavourites(userId, activityId);
    }

    @Override
    public Activity getActivity(String name, Double latitude, Double longitude) {
        return this.activityRepository.findByCoordinates(name, latitude, longitude).get();
    }

    @Override
    public void deleteFromFavourites(String userId, Long activityId) {
        this.activityRepository.deleteFromFavourites(userId, activityId);
    }

    @Override
    public List<Activity> getFavourites(String login) {
        return this.activityRepository.findFavourites(login);
    }

    @Override
    public List<Activity> getFavouritesByCityAndCategory(
            String login, String city, String category) {
        return this.activityRepository
                .findFavouritesByCityAndCategory(login, city, category);
    }

    @Override
    public Activity getById(Long id) {
        return this.activityRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean isExists(String userId, Long activityId) {
        return this.activityRepository.isExists(userId, activityId);
    }

    @Override
    public void update(Long id, String name, String description, String time, Double price, byte[] bytes) {
        this.activityRepository.update(
                id, name, description, DateTimeService.convert(time), price, bytes);
    }

    @Override
    public void delete(Long id) {
        Optional<Activity> activity = this.activityRepository.findById(id);
        this.activityRepository.deleteById(id);
        this.coordinateRepository.deleteById(activity.get().getCoordinate().getId());
    }

    @Override
    public List<ActivityStatus> getActivityStatuses(
            List<Activity> activities, HttpSession session,
            PreliminaryActivityService preliminaryActivityService) {

        List<ActivityStatus> activityStatuses = new ArrayList<>();
        List<PreliminaryActivity> addedActivities =
                preliminaryActivityService.getAll(session.getId());

        for (Activity activity : activities) {
            activityStatuses.add(new ActivityStatus(activity,
                    !addedActivities.stream()
                            .map(addedActivity -> addedActivity.getActivity().getId())
                            .toList()
                            .contains(activity.getId())));
        }

        return activityStatuses;
    }
}
