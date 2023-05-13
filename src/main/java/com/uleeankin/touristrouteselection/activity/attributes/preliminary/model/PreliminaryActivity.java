package com.uleeankin.touristrouteselection.activity.attributes.preliminary.model;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Data
@Table("chosen_activities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreliminaryActivity {

    private String id;
    private Activity activity;
    private boolean isEvent;
    private Time eventTime;

}
