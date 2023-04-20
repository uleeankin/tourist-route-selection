package com.uleeankin.touristrouteselection.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public final UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    public SecurityConfig(UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(userAuthenticationProvider)
                .eraseCredentials(false);
        return builder.build();
    }

    @Bean
    public AuthenticationSuccessHandler urlAuthenticationSuccessHandler() {
        return new UrlAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/register")
                .permitAll()
                .requestMatchers("/")
                .permitAll()
                .requestMatchers("/css/main.css", "/css/util.css")
                .permitAll()
                .requestMatchers("/css/labels.css", "/css/navigation_panel.css")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .loginPage("/")
                    .successHandler(urlAuthenticationSuccessHandler())
                    .failureUrl("/login.html?error=true")
                    .permitAll();

        return http.build();
    }
}
