package com.uleeankin.touristrouteselection.user.mapper;

import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.user.model.Organization;
import com.uleeankin.touristrouteselection.user.role.model.Role;
import com.uleeankin.touristrouteselection.user.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationRowMapper implements RowMapper<Organization> {
    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Organization(new User(rs.getString("login"),
                rs.getString("user_password"),
                new Role(rs.getLong("user_role"),
                        rs.getString("role_name")),
                new City(rs.getLong("city"),
                        rs.getString("city_name")),
                rs.getBoolean("user_status")),
                rs.getString("organization_name"));
    }
}
