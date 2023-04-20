package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.services.city.CityService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final CityService cityService;

    private final UserService userService;

    @Autowired
    public RegistrationController(CityService cityService, UserService userService) {
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping
    public String registerForm(Model model) {
        List<String> cities = this.cityService.getAll().stream().map(City::getName).toList();
        model.addAttribute("cities", cities);
        return "register";
    }

    @PostMapping
    public String processRegistration(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("lastname") String lastname,
            @RequestParam("cityname") String city
    ) {

        this.userService.saveTourist(login, password, "Турист",
                city, name, surname, lastname);

        return "redirect:/";
    }
}
