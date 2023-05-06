package com.uleeankin.touristrouteselection.activity.model;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityStatus {

    private Activity activity;

    private boolean status;
}
