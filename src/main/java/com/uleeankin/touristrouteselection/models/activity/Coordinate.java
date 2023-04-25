package com.uleeankin.touristrouteselection.models.activity;


import com.uleeankin.touristrouteselection.models.City;
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
