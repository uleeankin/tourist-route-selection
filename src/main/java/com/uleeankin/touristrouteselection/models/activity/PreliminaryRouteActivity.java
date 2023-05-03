package com.uleeankin.touristrouteselection.models.activity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Data
@Table("chosen_activities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreliminaryRouteActivity {

    private String id;
    private Long activityId;
    private boolean status;
    private Time eventTime;

}
