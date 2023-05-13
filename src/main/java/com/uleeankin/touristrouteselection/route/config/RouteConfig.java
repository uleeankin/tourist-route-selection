package com.uleeankin.touristrouteselection.route.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "route")
public class RouteConfig {

    private String save;
    private String addActivity;
    private String byNameAndOwner;
    private String byOwner;
    private String byId;
    private String notOwner;
    private String favourites;
    private String completed;
    private String activities;
    private String statusChanging;
    private String addFavourite;
    private String deleteFavourite;
    private String isFavourite;
    private String isCompleted;
    private String complete;
    private String usesNumber;

}
