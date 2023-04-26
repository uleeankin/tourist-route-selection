package com.uleeankin.touristrouteselection.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "activity")
public class ActivityConfig {

    private String activityAdding;
    private String all;
    private String byCity;
    private String byId;
    private String byCityAndCategory;
    private String toFavourite;
    private String deleteFromFavouriteQuery;
    private String allFavourites;
    private String favouritesByCityAndCategory;
    private String existence;

}
