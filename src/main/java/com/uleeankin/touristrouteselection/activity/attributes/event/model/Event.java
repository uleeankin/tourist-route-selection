package com.uleeankin.touristrouteselection.activity.attributes.event.model;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Activity activity;

    private Date startDate;

    private Date endDate;

    private String owner;

}
