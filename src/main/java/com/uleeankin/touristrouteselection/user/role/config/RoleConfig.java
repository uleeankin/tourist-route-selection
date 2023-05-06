package com.uleeankin.touristrouteselection.user.role.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "role")
public class RoleConfig {

    private String name;

}
