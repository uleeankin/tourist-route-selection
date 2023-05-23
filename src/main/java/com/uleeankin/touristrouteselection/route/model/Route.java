package com.uleeankin.touristrouteselection.route.model;

import com.uleeankin.touristrouteselection.city.model.City;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.sql.Time;


@Data
@Table("route")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    private Long id;

    private String name;

    private String description;

    private String owner;

    private Time time;

    private Double price;

    private Double length;

    private Boolean status;

    private Date creationDate;

    private City city;
}
