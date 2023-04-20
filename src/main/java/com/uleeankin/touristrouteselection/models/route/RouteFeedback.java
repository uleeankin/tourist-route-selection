package com.uleeankin.touristrouteselection.models.route;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
@Table(name = "route_feedback")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteFeedback {

    private String userLogin;

    private Long routeId;

    private Integer assessment;

    private String feedback;

    private Date date;

}
