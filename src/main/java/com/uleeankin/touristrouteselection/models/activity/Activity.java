package com.uleeankin.touristrouteselection.models.activity;

import com.uleeankin.touristrouteselection.algorithm.GraphNode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;

@Data
@Table(name = "activity")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Activity {

    @Id
    private Long id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private Category category;

    private byte[] photo;

    @NonNull
    private Double price;

    @NonNull
    private Time duration;

}
