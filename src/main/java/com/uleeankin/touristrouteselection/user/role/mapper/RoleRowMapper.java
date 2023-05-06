package com.uleeankin.touristrouteselection.user.role.mapper;

import com.uleeankin.touristrouteselection.user.role.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Role(rs.getLong("role_id"),
                rs.getString("role_name"));
    }
}
