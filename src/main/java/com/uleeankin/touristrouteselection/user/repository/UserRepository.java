package com.uleeankin.touristrouteselection.user.repository;

import com.uleeankin.touristrouteselection.user.model.Organization;
import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.user.model.User;

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
