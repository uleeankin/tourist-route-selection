package com.uleeankin.touristrouteselection.user.role.repository;

import com.uleeankin.touristrouteselection.user.role.model.Role;
import com.uleeankin.touristrouteselection.user.role.config.RoleConfig;
import com.uleeankin.touristrouteselection.user.role.mapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
