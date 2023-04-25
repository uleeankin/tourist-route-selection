package com.uleeankin.touristrouteselection.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {

    private String userAdding;
    private String statusChanging;
    private String userByLogin;
    private String touristAdding;
    private String moderators;
    private String touristByLogin;
    private String orgAdding;
    private String orgs;

}
