package com.uleeankin.touristrouteselection.route.model;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CreatedRoute {

    private List<PreliminaryActivity> activities;

    private Time routeTime;

    private Double routePrice;

    private Double routeDuration;
}
