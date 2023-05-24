package com.uleeankin.touristrouteselection.route.service;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import com.uleeankin.touristrouteselection.route.repository.AgencyRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
