package com.uleeankin.touristrouteselection.repositories.user;

import com.uleeankin.touristrouteselection.models.user.Organization;
import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;

import java.util.List;

public interface UserRepository {

    User findByLogin(String login);

    List<User> findAllModerators();

    List<Organization> findOrganizationsByRole(String roleName);

    void saveUser(String login, String password, Long role, Long city);

    void saveTourist(String login, String name, String surname, String lastname);

    void saveOrganization(String login, String name);

    void changeStatus(String login, boolean newStatus);

    Tourist findTouristByLogin(String login);

}
