package com.uleeankin.touristrouteselection.route.service;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import com.uleeankin.touristrouteselection.route.model.Booking;
import com.uleeankin.touristrouteselection.route.model.Route;

import java.util.List;

public interface AgencyRouteService {

    AgencyRoute getById(Long id);
    boolean isBookedRoute(Long id);
    Integer getFreePlaceNumber(Long id, String date);
    void bookRoute(Long routeId, String userId, String bookingDate, Integer touristNumber);
    List<Route> getAllBooked(String userId);
    List<Booking> getBookingInfo(Long routeId);

}
