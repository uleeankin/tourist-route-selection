package com.uleeankin.touristrouteselection.models.activity;

import com.uleeankin.touristrouteselection.algorithm.GraphNode;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Data
@Table("chosen_activities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreliminaryRouteActivity implements GraphNode {

    private String id;
    private Activity activity;
    private boolean isCompulsory;
    private boolean isEvent;
    private Time eventTime;

}
