package com.uleeankin.touristrouteselection.city.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "city")
public class CityConfig {

    private String all;
    private String one;
    private String save;

}
