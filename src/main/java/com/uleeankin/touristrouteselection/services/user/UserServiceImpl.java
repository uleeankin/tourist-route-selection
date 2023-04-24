package com.uleeankin.touristrouteselection.services.user;

import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.repositories.city.CityRepository;
import com.uleeankin.touristrouteselection.repositories.role.RoleRepository;
import com.uleeankin.touristrouteselection.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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
    public Optional<User> getByLogin(String login) {
        return Optional.ofNullable(this.repository.findByLogin(login));
    }

    @Override
    public Tourist getTouristByLogin(String login) {
        return this.repository.findTouristByLogin(login);
    }
}
