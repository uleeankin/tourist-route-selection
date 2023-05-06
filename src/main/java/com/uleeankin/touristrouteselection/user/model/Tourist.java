package com.uleeankin.touristrouteselection.user.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "tourist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tourist {

    private User user;

    private String name;

    private String surname;

    private String lastname;

}
