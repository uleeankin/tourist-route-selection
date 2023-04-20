package com.uleeankin.touristrouteselection.models.route;

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
