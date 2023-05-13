package com.uleeankin.touristrouteselection.route.service;

import com.uleeankin.touristrouteselection.route.model.CompletedRoute;
import com.uleeankin.touristrouteselection.route.model.CreatedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.activity.model.Activity;

import java.util.List;

public interface RouteService {

    void save(String name, String description,
              String owner, String city, byte[] photo, CreatedRoute route);

    void changeStatus(Long routeId);

    Route getByOwnerAndName(String owner, String name);

    List<Activity> getRouteActivities(Long routeId);

    List<Route> getByOwner(String owner);

    List<Route> getAllWithoutOwn(String userLogin);

    Route getById(Long routeId);

    void addToFavourites(String userId, Long routeId);

    void deleteFromFavourites(String userId, Long routeId);

    boolean isFavourite(String userId, Long routeId);

    boolean isCompleted(String userId, Long routeId);

    void completeRoute(String userLogin, Long routeId);

    List<Route> getFavourites(String userLogin);

    List<CompletedRoute> getCompleted(String userLogin);

    Integer getRouteUsesNumber(Long routeId);
}
