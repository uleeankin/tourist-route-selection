package com.uleeankin.touristrouteselection.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;

    @Autowired
    public UserAuthenticationProvider(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = this.userService.loadUserByUsername(login);

        if (checkUserData(login, password, user)) {
            return new UsernamePasswordAuthenticationToken(login, password, user.getAuthorities());
        } else {
            return null;
        }
    }

    private boolean checkUserData(String login, String password, UserDetails user) {
        return user.getUsername().equals(login)
                && user.getPassword().equals(password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
