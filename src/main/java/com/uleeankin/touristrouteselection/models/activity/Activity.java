package com.uleeankin.touristrouteselection.models.activity;

import com.uleeankin.touristrouteselection.algorithm.GraphNode;
import com.uleeankin.touristrouteselection.models.City;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Data
@Table(name = "activities")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Activity implements GraphNode {

    @Id
    private Long id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private Category category;

    @NonNull
    private City city;

    @NonNull
    private Double price;

    @NonNull
    private Time duration;

}
