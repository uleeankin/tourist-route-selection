package com.uleeankin.touristroutebeta.models.report;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RouteCreationReport {

    private String cityName;

    private Double createdRouteNumber;
}
