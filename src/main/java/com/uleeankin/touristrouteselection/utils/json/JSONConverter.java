package com.uleeankin.touristrouteselection.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.utils.json.model.GeographicalCoordinate;
import com.uleeankin.touristrouteselection.utils.json.model.Location;

import java.util.ArrayList;
import java.util.List;

public class JSONConverter {

    public String getCoordinatesJSON(List<Activity> activities) {
        List<Location> locations = this.getLocations(activities);

        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    private List<Location> getLocations(List<Activity> activities) {
        List<Location> locations = new ArrayList<>();

        for (Activity activity : activities) {
            locations.add(new Location(new GeographicalCoordinate(
                    activity.getCoordinate().getLatitude(),
                    activity.getCoordinate().getLongitude())));
        }

        return locations;
    }
}
