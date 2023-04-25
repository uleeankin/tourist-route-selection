package com.uleeankin.touristrouteselection.repositories.role;

import com.uleeankin.touristrouteselection.models.user.Role;
import com.uleeankin.touristrouteselection.utils.config.RoleConfig;
import com.uleeankin.touristrouteselection.utils.mappers.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleConfig roleConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(RoleConfig roleConfig, JdbcTemplate jdbcTemplate) {
        this.roleConfig = roleConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Role> findByName(String role) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                this.roleConfig.getName(), new RoleRowMapper(), role));
    }
}
