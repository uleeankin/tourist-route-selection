package com.uleeankin.touristrouteselection.user.model;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.user.role.model.Role;
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
