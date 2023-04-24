package com.uleeankin.touristrouteselection.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserQueryConfigurator {

    public String userAdding;
    public String statusChanging;
    public String userByLogin;
    public String touristAdding;
    public String moderators;
    public String touristByLogin;

}
