package com.uleeankin.touristrouteselection.models.activity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
@Table(name = "activity_feedback")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityFeedback {

    private String userLogin;

    private Long activityId;

    private Integer assessment;

    private String feedback;

    private Date date;

}
