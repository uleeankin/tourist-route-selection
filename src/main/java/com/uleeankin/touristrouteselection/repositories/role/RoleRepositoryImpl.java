package com.uleeankin.touristrouteselection.repositories.role;

import com.uleeankin.touristrouteselection.models.user.Role;
import com.uleeankin.touristrouteselection.utils.mappers.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private static final String ROLE_BY_NAME =
            "select role_id, role_name from roles where role_name = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Role> findByName(String role) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(ROLE_BY_NAME, new RoleRowMapper(), role));
    }
}
