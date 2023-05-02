package com.uleeankin.touristrouteselection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableConfigurationProperties
@SpringBootApplication
@EnableJdbcHttpSession
public class TouristRouteSelectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristRouteSelectionApplication.class, args);
    }

}
