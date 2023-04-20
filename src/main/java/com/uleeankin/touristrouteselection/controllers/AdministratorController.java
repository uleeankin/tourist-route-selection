package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.services.category.CategoryService;
import com.uleeankin.touristrouteselection.services.city.CityService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    private final CityService cityService;
    private final CategoryService categoryService;

    private final UserService userService;

    @Autowired
    public AdministratorController(CityService cityService,
                                   CategoryService categoryService,
                                   UserService userService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        SessionContext.addUserNameToPage(model);
        return "admin/adminMain";
    }

    @GetMapping("/moderators")
    public String getModeratorsPage(Model model) {
        List<String> cities = this.cityService.getAll().stream().map(City::getName).toList();

        model.addAttribute("cities", cities);
        model.addAttribute("moderators", this.userService.getAllModerators());
        SessionContext.addUserNameToPage(model);
        return "admin/adminModerators";
    }

    @PostMapping("/moderators")
    public String addModerator(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("cityname") String city) {

        this.userService.saveUser(login, password, "Модератор", city);

        return "redirect:/admin/moderators";
    }

    @PostMapping("/moderators/{login}")
    public String changeModeratorStatus(@PathVariable("login") String login) {
        this.userService.changeStatus(login);
        return "redirect:/admin/moderators";
    }

    @GetMapping("/tourists")
    public String getTouristsPage(Model model) {
        model.addAttribute("tourists", this.userService.getAllTourists());
        SessionContext.addUserNameToPage(model);
        return "admin/adminTourists";
    }

    @GetMapping("/cities")
    public String getCitiesPage(Model model) {
        model.addAttribute("cities", this.cityService.getAll());
        SessionContext.addUserNameToPage(model);
        return "admin/adminCities";
    }

    @PostMapping("/cities")
    public String addCity(@RequestParam("city") String cityName, Model model) {
        if (!cityName.isEmpty()) {
            this.cityService.save(cityName);
        }
        return "redirect:/admin/cities";
    }

    @GetMapping("/categories")
    public String getCategoryPage(Model model) {
        model.addAttribute("categories",
                this.categoryService.getAll());
        SessionContext.addUserNameToPage(model);
        return "admin/adminCategories";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam("category") String categoryName, Model model) {
        if (!categoryName.isEmpty()) {
            this.categoryService.save(categoryName);
        }
        return "redirect:/admin/categories";
    }
}
