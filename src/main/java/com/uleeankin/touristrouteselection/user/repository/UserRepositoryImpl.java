package com.uleeankin.touristrouteselection.user.repository;

import com.uleeankin.touristrouteselection.user.model.Organization;
import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.user.model.User;
import com.uleeankin.touristrouteselection.user.config.UserConfig;
import com.uleeankin.touristrouteselection.user.mapper.OrganizationRowMapper;
import com.uleeankin.touristrouteselection.user.mapper.TouristRowMapper;
import com.uleeankin.touristrouteselection.user.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserConfig queryConfigurator;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(UserConfig queryConfigurator, JdbcTemplate jdbcTemplate) {
        this.queryConfigurator = queryConfigurator;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByLogin(String login) {
        return this.jdbcTemplate.queryForObject(
                this.queryConfigurator.getUserByLogin(), new UserRowMapper(), login);
    }

    @Override
    public List<User> findAllModerators() {
        return this.jdbcTemplate.query(
                this.queryConfigurator.getModerators(), new UserRowMapper(), "Модератор");
    }

    @Override
    public List<Organization> findOrganizationsByRole(String roleName) {
        return this.jdbcTemplate.query(
                this.queryConfigurator.getOrgs(), new OrganizationRowMapper(), roleName);
    }

    @Override
    public void saveUser(String login, String password, Long role, Long city) {
        this.jdbcTemplate.update(this.queryConfigurator.getUserAdding(), login, password, role, city);
    }

    @Override
    public void saveTourist(String login, String name, String surname, String lastname) {
        this.jdbcTemplate.update(
                this.queryConfigurator.getTouristAdding(), login, name, surname, lastname);
    }

    @Override
    public void saveOrganization(String login, String name) {
        this.jdbcTemplate.update(
                this.queryConfigurator.getOrgAdding(), login, name);
    }

    @Override
    public void changeStatus(String login, boolean newStatus) {
        this.jdbcTemplate.update(this.queryConfigurator.getStatusChanging(), newStatus, login);
    }

    @Override
    public Tourist findTouristByLogin(String login) {
        return this.jdbcTemplate.queryForObject(
                this.queryConfigurator.getTouristByLogin(), new TouristRowMapper(), login);
    }
}
