package com.uleeankin.touristrouteselection.user.service;

import com.uleeankin.touristrouteselection.user.model.Organization;
import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.user.model.User;
import com.uleeankin.touristrouteselection.city.repository.CityRepository;
import com.uleeankin.touristrouteselection.user.role.repository.RoleRepository;
import com.uleeankin.touristrouteselection.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String AGENCY = "Туристическое агентство";
    private static final String ORGANIZATION = "Организация";

    private final UserRepository repository;

    private final CityRepository cityRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           CityRepository cityRepository,
                           RoleRepository roleRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllModerators() {
        return this.repository.findAllModerators();
    }

    @Override
    public void saveUser(String login, String password, String roleName, String cityName) {
        Long role = this.roleRepository.findByName(roleName).get().getId();
        Long city = this.cityRepository.findByName(cityName).get().getId();
        this.repository.saveUser(login, password, role, city);
    }

    @Override
    public void changeStatus(String login) {
        User user = this.repository.findByLogin(login);
        this.repository.changeStatus(login, !user.getStatus());
    }

    @Override
    public void saveTourist(String login, String password, String roleName,
                            String cityName, String name, String surname, String lastname) {
        Long role = this.roleRepository.findByName(roleName).get().getId();
        Long city = this.cityRepository.findByName(cityName).get().getId();
        this.repository.saveUser(login, password, role, city);
        this.repository.saveTourist(login, name, surname, lastname);
    }

    @Override
    public void saveOrganization(String login, String password,
                                 String roleName, String cityName, String name) {
        Long role = this.roleRepository.findByName(roleName).get().getId();
        Long city = this.cityRepository.findByName(cityName).get().getId();
        this.repository.saveUser(login, password, role, city);
        this.repository.saveOrganization(login, name);
    }

    @Override
    public List<Organization> getAllAgencies() {
        return this.repository.findOrganizationsByRole(AGENCY);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return this.repository.findOrganizationsByRole(ORGANIZATION);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.ofNullable(this.repository.findByLogin(login));
    }

    @Override
    public Tourist getTouristByLogin(String login) {
        return this.repository.findTouristByLogin(login);
    }
}
