package com.uleeankin.touristrouteselection.activity.model;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
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
