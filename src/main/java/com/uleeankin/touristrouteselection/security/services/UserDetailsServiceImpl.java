package com.uleeankin.touristrouteselection.security.services;

import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user " + username);
        }

        return org.springframework.security.core.userdetails.User.withUsername(
                user.getLogin())
                .password(user.getPassword())
                .authorities(user.getRole().getName()).build();
    }
}
