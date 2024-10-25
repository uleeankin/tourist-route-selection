package com.uleeankin.touristrouteselection.activity.model;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.model.Coordinate;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

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

    private Map<DurationCategory, Time> rangedDuration;

    private Boolean isFamilyFriendly;

    private Boolean isDisabledPersonAccessible;

    private Boolean isPetFriendly;

    private Boolean hasPhotoArea;

    private Integer ageRestriction;

    private Date foundationDate;

    private Long traffic;

    public Activity(Long id,
                    String name,
                    String description,
                    Coordinate coordinate,
                    Category category,
                    byte[] photo,
                    Double price,
                    Time duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate = coordinate;
        this.category = category;
        this.photo = photo;
        this.price = price;
        this.duration = duration;
    }
}
