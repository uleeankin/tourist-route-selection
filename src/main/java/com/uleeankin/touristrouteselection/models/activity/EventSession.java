package com.uleeankin.touristrouteselection.models.activity;

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
