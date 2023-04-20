package com.uleeankin.touristrouteselection.models.user;

import com.uleeankin.touristrouteselection.models.City;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String login;

    private String password;

    private Role role;

    private City city;

    private Boolean status;

}
