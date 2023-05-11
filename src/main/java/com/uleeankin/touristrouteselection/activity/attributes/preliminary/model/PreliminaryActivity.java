package com.uleeankin.touristrouteselection.activity.attributes.preliminary.model;

import com.uleeankin.touristrouteselection.activity.model.Activity;
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
public class PreliminaryActivity implements GraphNode {

    private String id;
    private Activity activity;
    private boolean isCompulsory;
    private boolean isEvent;
    private Time eventTime;

    @Override
    public Long getNodeId() {
        return this.activity.getId();
    }
}
