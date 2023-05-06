package com.uleeankin.touristrouteselection.activity.attributes.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSession {

    private Long eventId;

    private Time sessionTime;
}
