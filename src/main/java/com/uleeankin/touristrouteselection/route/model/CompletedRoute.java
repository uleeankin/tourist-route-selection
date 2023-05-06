package com.uleeankin.touristrouteselection.route.model;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompletedRoute {

    private Route route;

    private Date creationDate;

}
