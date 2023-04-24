package com.uleeankin.touristrouteselection.repositories.user;

import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.utils.config.UserQueryConfigurator;
import com.uleeankin.touristrouteselection.utils.mappers.TouristRowMapper;
import com.uleeankin.touristrouteselection.utils.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserQueryConfigurator queryConfigurator;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(UserQueryConfigurator queryConfigurator, JdbcTemplate jdbcTemplate) {
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
        System.out.println(this.queryConfigurator.getModerators());
        return this.jdbcTemplate.query(
                this.queryConfigurator.getModerators(), new UserRowMapper(), "Модератор");
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
    public void changeStatus(String login, boolean newStatus) {
        this.jdbcTemplate.update(this.queryConfigurator.getStatusChanging(), newStatus, login);
    }

    @Override
    public Tourist findTouristByLogin(String login) {
        return this.jdbcTemplate.queryForObject(
                this.queryConfigurator.getTouristByLogin(), new TouristRowMapper(), login);
    }
}
