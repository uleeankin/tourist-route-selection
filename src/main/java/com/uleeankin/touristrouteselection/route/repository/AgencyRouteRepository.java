package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;

import java.sql.Date;

public interface AgencyRouteRepository {
    AgencyRoute findById(Long id);
    boolean isBooked(Long id);
    Integer countFreePlaces(Long id, Date date);
    void bookRoute(Long routeId, String userId, Date bookingDate, Integer touristNumber);
}
