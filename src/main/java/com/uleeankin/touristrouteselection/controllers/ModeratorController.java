package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.category.CategoryService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final ActivityService activityService;

    @Autowired
    public ModeratorController(UserService userService,
                               CategoryService categoryService,
                               ActivityService activityService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        SessionContext.addUserNameToPage(model);
        return "moderator/moderatorMain";
    }

    @GetMapping("/places")
    public String getAllPlacesPage(Model model) {
        this.setModeratorData(model);

        User user = this.userService.getByLogin(SessionContext.getUserLogin()).get();
        List<Activity> activities = this.activityService.getByCity(user.getCity().getName());
        model.addAttribute("activities", activities);

        return "moderator/moderatorAllPlaces";
    }

    @GetMapping("places/manage")
    public String getPlacesManagementPage(Model model) {
        this.setModeratorData(model);

        List<String> categories =
                this.categoryService.getAll().
                        stream().map(Category::getName).toList();

        model.addAttribute("categories", categories);

        return "moderator/managePlaces";
    }

    @PostMapping("places/manage")
    public String addNewActivity(@RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("latitude") Double latitude,
                                 @RequestParam("longitude") Double longitude,
                                 @RequestParam("categoryList") String category,
                                 @RequestParam("time") String time,
                                 @RequestParam("price") Double price) {


        User user = this.userService.getByLogin(SessionContext.getUserLogin()).get();
        this.activityService.addActivity(name, description, user.getCity().getName(),
                category, latitude, longitude, time, price);

        return "redirect:/moderator/places";
    }

    private void setModeratorData(Model model) {
        SessionContext.addUserNameToPage(model);
        User user = this.userService.getByLogin(SessionContext.getUserLogin()).get();
        model.addAttribute("city", user.getCity().getName());
    }
}
