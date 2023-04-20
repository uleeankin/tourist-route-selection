package com.uleeankin.touristrouteselection.repositories.user;

import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.utils.mappers.TouristRowMapper;
import com.uleeankin.touristrouteselection.utils.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String USER_BY_LOGIN =
            "select login, user_password, user_role, " +
            "role_name, user_info.city, city_name, user_status " +
            "from (user_info join city on user_info.city = city.city_id) " +
            "join roles on user_info.user_role = roles.role_id where login = ?";

    private static final String ALL_TOURISTS =
            "select user_info.login, user_password, user_role, role_name, user_info.city, city_name, user_status, " +
                    "tourist_name, tourist_surname, tourist_lastname\n" +
                    "  from ((user_info join city on user_info.city = city.city_id)\n" +
                    "                join roles on user_info.user_role = roles.role_id)\n" +
                    "                join tourist on user_info.login = tourist.login where role_name = 'Турист';";

    private static final String TOURIST_BY_LOGIN =
            "select user_info.login, user_password, user_role, role_name, user_info.city, city_name, user_status, " +
                    "tourist_name, tourist_surname, tourist_lastname\n" +
                    "  from ((user_info join city on user_info.city = city.city_id)\n" +
                    "                join roles on user_info.user_role = roles.role_id)\n" +
                    "                join tourist on user_info.login = tourist.login where user_info.login = ?;";

    private static final String ALL_MODERATORS =
            "select user_info.login, user_password, user_role, role_name, user_info.city, city_name, user_status\n" +
                    "  from (user_info join city on user_info.city = city.city_id)\n" +
                    "                join roles on user_info.user_role = roles.role_id \n" +
                    "                where role_name = 'Модератор';";

    private static final String ADD_USER =
            "insert into user_info (login, user_password, user_role, city)" +
                    " values (?, ?, ?, ?)";

    private static final String ADD_TOURIST =
            "insert into tourist (login, tourist_name, tourist_surname, tourist_lastname) " +
                    "values (?, ?, ?, ?)";

    private static final String CHANGE_STATUS =
            "update user_info set user_status = ? where login = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByLogin(String login) {
        return this.jdbcTemplate.queryForObject(USER_BY_LOGIN, new UserRowMapper(), login);
    }

    @Override
    public List<Tourist> findAllTourists() {
        return this.jdbcTemplate.query(ALL_TOURISTS, new TouristRowMapper());
    }

    @Override
    public List<User> findAllModerators() {
        return this.jdbcTemplate.query(ALL_MODERATORS, new UserRowMapper());
    }

    @Override
    public void saveUser(String login, String password, Long role, Long city) {
        this.jdbcTemplate.update(ADD_USER, login, password, role, city);
    }

    @Override
    public void saveTourist(String login, String name, String surname, String lastname) {
        this.jdbcTemplate.update(ADD_TOURIST, login, name, surname, lastname);
    }

    @Override
    public void changeStatus(String login, boolean newStatus) {
        this.jdbcTemplate.update(CHANGE_STATUS, newStatus, login);
    }

    @Override
    public Tourist findTouristByLogin(String login) {
        return this.jdbcTemplate.queryForObject(TOURIST_BY_LOGIN, new TouristRowMapper(), login);
    }
}
