package com.uleeankin.touristrouteselection.user.service;

import com.uleeankin.touristrouteselection.user.model.Organization;
import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllModerators();

    void saveUser(String login, String password, String roleName, String cityName);

    void changeStatus(String login);

    void saveTourist(String login, String password, String roleName,
                     String cityName, String name, String surname, String lastname);

    void saveOrganization(String login, String password,
                          String roleName, String cityName, String name);

    List<Organization> getAllAgencies();

    List<Organization> getAllOrganizations();

    Optional<User> getByLogin(String login);

    Tourist getTouristByLogin(String login);
}
