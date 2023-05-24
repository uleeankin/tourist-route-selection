package com.uleeankin.touristrouteselection.route.repository;

import com.uleeankin.touristrouteselection.route.model.AgencyRoute;

public interface AgencyRouteRepository {
    AgencyRoute findById(Long id);
}
