package com.uleeankin.touristrouteselection.activity.attributes.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
public class EventInfo {

    private Event event;

    private List<Time> sessions;
}
