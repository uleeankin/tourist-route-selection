package com.uleeankin.touristrouteselection.route.model;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgencyRoute {
    private Route route;
    private Date startDate;
    private Date endDate;
    private Integer maxTouristNumber;
}
