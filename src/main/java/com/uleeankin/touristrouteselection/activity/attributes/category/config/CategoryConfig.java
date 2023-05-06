package com.uleeankin.touristrouteselection.activity.attributes.category.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "category")
public class CategoryConfig {

    private String all;
    private String one;
    private String save;

}
