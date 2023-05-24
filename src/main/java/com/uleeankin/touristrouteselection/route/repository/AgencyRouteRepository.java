package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import com.uleeankin.touristrouteselection.route.model.Route;

import java.sql.Date;
import java.util.List;

public interface AgencyRouteRepository {
    AgencyRoute findById(Long id);
    boolean isBooked(Long id);
    Integer countFreePlaces(Long id, Date date);
    void bookRoute(Long routeId, String userId, Date bookingDate, Integer touristNumber);
    List<Route> findAllBooked(String userId);
}
