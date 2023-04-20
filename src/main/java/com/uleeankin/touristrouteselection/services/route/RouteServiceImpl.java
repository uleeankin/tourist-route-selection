package com.uleeankin.touristrouteselection.services.route;

import com.uleeankin.touristrouteselection.models.route.CompletedRoute;
import com.uleeankin.touristrouteselection.models.route.Route;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.repositories.city.CityRepository;
import com.uleeankin.touristrouteselection.repositories.route.RouteRepository;
import com.uleeankin.touristrouteselection.utils.RouteDurationCalculator;
import com.uleeankin.touristrouteselection.utils.RouteTimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteDurationCalculator durationCalculator;

    private final RouteTimeCalculator routeTimeCalculator;

    private final RouteRepository routeRepository;

    private final CityRepository cityRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository,
                            CityRepository cityRepository) {
        this.durationCalculator = new RouteDurationCalculator();
        this.routeTimeCalculator = new RouteTimeCalculator();
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void save(String name, String description,
                     String owner, String city, List<Activity> activities) {

        double price = activities.stream().mapToDouble(Activity::getPrice).sum();
        double duration = this.durationCalculator.getDuration(activities);
        Time time = this.routeTimeCalculator.getTime(activities);
        Date creationDate = new Date(new java.util.Date().getTime());
        Long cityId = this.cityRepository.findByName(city).get().getId();

        this.routeRepository.save(name, description, owner, time,
                price, duration, creationDate, cityId);

        Route route = this.routeRepository.findByNameAndOwner(name, owner);

        for (Activity activity : activities) {
            this.routeRepository.saveActivityToRoute(route.getId(), activity.getId());
        }
    }

    @Override
    public void changeStatus(Long routeId) {
        Route route = this.routeRepository.getById(routeId);
        this.routeRepository.changeStatus(routeId, !route.getStatus());
    }

    @Override
    public Route getByOwnerAndName(String owner, String name) {
        return this.routeRepository.findByNameAndOwner(name, owner);
    }

    @Override
    public List<Activity> getRouteActivities(Long routeId) {
        return this.routeRepository.findAllByRoute(routeId);
    }

    @Override
    public List<Route> getByOwner(String owner) {
        return this.roundLength(this.routeRepository.findAllByOwner(owner));
    }

    @Override
    public List<Route> getAllWithoutOwn(String userLogin) {
        return this.roundLength(this.routeRepository.findAllWithoutOwn(userLogin));
    }

    @Override
    public Route getById(Long routeId) {
        Route route = this.routeRepository.getById(routeId);
        route.setLength(Math.ceil(route.getLength() * 10) / 10.0);
        return route;
    }

    @Override
    public void addToFavourites(String userId, Long routeId) {
        this.routeRepository.addToFavourites(userId, routeId);
    }

    @Override
    public void deleteFromFavourites(String userId, Long routeId) {
        this.routeRepository.deleteFromFavourites(userId, routeId);
    }

    @Override
    public boolean isFavourite(String userId, Long routeId) {
        return this.routeRepository.isFavourite(userId, routeId);
    }

    @Override
    public boolean isCompleted(String userId, Long routeId) {
        return this.routeRepository.isCompleted(userId, routeId);
    }

    @Override
    public void completeRoute(String userLogin, Long routeId) {
        this.routeRepository.completeRoute(userLogin, routeId,
                new Date(new java.util.Date().getTime()));
    }

    @Override
    public List<Route> getFavourites(String userLogin) {
        return this.roundLength(this.routeRepository.findFavourites(userLogin));
    }

    @Override
    public List<CompletedRoute> getCompleted(String userLogin) {
        return this.roundLengthForCompletedRoute(
                this.routeRepository.findCompletedRoutes(userLogin));
    }

    @Override
    public Integer getRouteUsesNumber(Long routeId) {
        return this.routeRepository.getRouteUsesNumber(routeId);
    }

    private List<Route> roundLength(List<Route> routes) {
        for (Route route : routes) {
            route.setLength(Math.ceil(route.getLength() * 10) / 10.0);
        }
        return routes;
    }

    private List<CompletedRoute> roundLengthForCompletedRoute(List<CompletedRoute> routes) {
        for (CompletedRoute route : routes) {
            route.getRoute().setLength(Math.ceil(route.getRoute().getLength() * 10) / 10.0);
        }
        return routes;
    }
}
