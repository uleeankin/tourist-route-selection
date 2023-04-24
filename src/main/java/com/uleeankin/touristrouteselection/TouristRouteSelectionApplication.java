package com.uleeankin.touristrouteselection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class TouristRouteSelectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristRouteSelectionApplication.class, args);
    }

}
