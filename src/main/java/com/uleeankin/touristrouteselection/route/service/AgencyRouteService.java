package com.uleeankin.touristrouteselection.route.service;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;

public interface AgencyRouteService {

    AgencyRoute getById(Long id);
    boolean isBookedRoute(Long id);
    Integer getFreePlaceNumber(Long id, String date);
    void bookRoute(Long routeId, String userId, String bookingDate, Integer touristNumber);

}
