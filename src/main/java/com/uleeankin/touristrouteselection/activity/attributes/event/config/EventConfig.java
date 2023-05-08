package com.uleeankin.touristrouteselection.activity.attributes.event.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "event")
public class EventConfig {
    private String update;
    private String save;
    private String saveSession;
    private String all;
    private String byDate;
    private String byId;
    private String delete;
    private String deleteSession;
    private String schedule;
    private String deleteEventSession;
    private String addSession;
    private String favourites;
    private String favouritesInRoute;
}
