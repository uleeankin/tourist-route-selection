package com.uleeankin.touristrouteselection.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "preliminary")
public class PreliminaryActivityConfig {

    private String save;
    private String updateStatus;
    private String deleteAll;

    private String deleteById;
    private String updateTime;
    private String all;
    private String allPreliminary;

}
