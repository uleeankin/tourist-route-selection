package com.uleeankin.touristrouteselection.route.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "agency")
public class AgencyRouteConfig {
    private String byId;
    private String isBooked;
    private String freePlaces;
    private String bookRoute;
}
