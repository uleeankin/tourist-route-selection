package com.uleeankin.touristrouteselection.route.service;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import com.uleeankin.touristrouteselection.route.model.Booking;
import com.uleeankin.touristrouteselection.route.model.BookingInfo;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.route.repository.AgencyRouteRepository;
import com.uleeankin.touristrouteselection.utils.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgencyRouteServiceImpl implements AgencyRouteService {

    private final AgencyRouteRepository agencyRouteRepository;

    @Autowired
    public AgencyRouteServiceImpl(AgencyRouteRepository agencyRouteRepository) {
        this.agencyRouteRepository = agencyRouteRepository;
    }

    @Override
    public AgencyRoute getById(Long id) {
        return this.agencyRouteRepository.findById(id);
    }

    @Override
    public boolean isBookedRoute(Long id) {
        return this.agencyRouteRepository.isBooked(id);
    }

    @Override
    public Integer getFreePlaceNumber(Long id, String date) {
        return this.agencyRouteRepository.countFreePlaces(id, Date.valueOf(date));
    }

    @Override
    public void bookRoute(Long routeId, String userId, String bookingDate, Integer touristNumber) {
        this.agencyRouteRepository.bookRoute(routeId, userId, Date.valueOf(bookingDate), touristNumber);
    }

    @Override
    public List<Route> getAllBooked(String userId) {
        return this.agencyRouteRepository.findAllBooked(userId);
    }

    @Override
    public List<Booking> getBookingInfo(Long routeId) {
        AgencyRoute route = this.agencyRouteRepository.findById(routeId);
        List<Date> dates = DateTimeService.getDates(
                route.getStartDate(), route.getEndDate());

        List<Booking> bookings = new ArrayList<>();

        for (Date date : dates) {
            bookings.add(new Booking(date,
                    this.convertToString(
                            this.agencyRouteRepository.findAllBookings(
                                    date, routeId))));
        }
        return bookings;
    }

    private String convertToString(List<BookingInfo> bookings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (BookingInfo info : bookings) {
            stringBuilder.append(info.toString());
        }

        return stringBuilder.toString();
    }
}
