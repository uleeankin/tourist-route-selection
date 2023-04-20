package com.uleeankin.touristrouteselection.services.user;

import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Tourist> getAllTourists();

    List<User> getAllModerators();

    void saveUser(String login, String password, String roleName, String cityName);

    void changeStatus(String login);

    void saveTourist(String login, String password, String roleName,
                     String cityName, String name, String surname, String lastname);

    Optional<User> getByLogin(String login);

    Tourist getTouristByLogin(String login);
}
