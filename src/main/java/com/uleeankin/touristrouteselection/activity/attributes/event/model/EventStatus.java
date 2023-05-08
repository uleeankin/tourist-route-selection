package com.uleeankin.touristrouteselection.activity.attributes.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventStatus {

    private Event event;
    private boolean status;
}
