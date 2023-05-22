package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.model.CompletedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.activity.model.Activity;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface RouteRepository {

    void save(String name, String description, String owner, Time duration,
              double price, double length, Date creationDate, byte[] photo, Long city);

    void saveActivityToRoute(Long routeId, Long activityId);

    void changeStatus(Long routeId, boolean newStatus);

    Route findByNameAndOwner(String name, String owner);

    List<Activity> findAllByRoute(Long routeId);

    List<Route> findAllByOwner(String owner);

    List<Route> findAllWithoutOwn(String userLogin);

    Route getById(Long routeId);

    void addToFavourites(String userId, Long routeId);

    void deleteFromFavourites(String userId, Long routeId);

    boolean isFavourite(String userId, Long routeId);

    boolean isCompleted(String userId, Long routeId);

    List<Route> findFavourites(String login);

    void completeRoute(String userLogin, Long routeId, Date completionDate);

    List<CompletedRoute> findCompletedRoutes(String userLogin);

    Integer getRouteUsesNumber(Long routeId);

    void saveAgencyRoute(Long id, Date startDate, Date endDate, Integer maxTouristsNumber);
}
