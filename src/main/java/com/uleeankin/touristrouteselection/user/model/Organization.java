package com.uleeankin.touristrouteselection.user.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "organization")
@AllArgsConstructor@NoArgsConstructor
@Getter
@Setter
public class Organization {

    private User user;

    private String name;

}
