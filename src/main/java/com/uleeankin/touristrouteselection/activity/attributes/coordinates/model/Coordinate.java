package com.uleeankin.touristrouteselection.activity.attributes.coordinates.model;


import com.uleeankin.touristrouteselection.city.model.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "coordinate")
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {

    @Id
    private Long id;

    private double latitude;

    private double longitude;

    private City city;

}
