package com.uleeankin.touristrouteselection.utils.mappers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.user.Role;
import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristRowMapper implements RowMapper<Tourist> {
    @Override
    public Tourist mapRow(ResultSet rs, int rowNum) throws SQLException {
        String lastname = rs.getString("tourist_lastname") == null
                ? "" : rs.getString("tourist_lastname");
        return new Tourist(new User(rs.getString("login"),
                rs.getString("user_password"),
                new Role(rs.getLong("user_role"),
                        rs.getString("role_name")),
                new City(rs.getLong("city"),
                        rs.getString("city_name")),
                rs.getBoolean("user_status")),
                rs.getString("tourist_name"),
                rs.getString("tourist_surname"),
                 lastname);
    }
}
