package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.user.model.User;
import com.uleeankin.touristrouteselection.activity.service.ActivityService;
import com.uleeankin.touristrouteselection.activity.attributes.category.service.CategoryService;
import com.uleeankin.touristrouteselection.user.service.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private static final String MODEL_AGENCY_NAME = "Агентство";
    private static final String DB_AGENCY_NAME = "Туристическое агентство";

    private final UserService userService;

    private final CategoryService categoryService;

    private final ActivityService activityService;

    private final SessionContext sessionContext;

    @Autowired
    public ModeratorController(UserService userService,
                               CategoryService categoryService,
                               ActivityService activityService, SessionContext sessionContext) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.sessionContext = sessionContext;
    }

    @GetMapping
    public String getMainPage(Model model) {
        sessionContext.addUserNameToPage(model);
        return "moderator/moderatorMain";
    }

    @GetMapping("/places")
    public String getAllPlacesPage(Model model) {
        this.setModeratorData(model);

        User user = this.userService.getByLogin(sessionContext.getUserLogin()).get();
        List<Activity> activities = this.activityService.getByCity(user.getCity().getName());
        model.addAttribute("activities", activities);

        return "moderator/moderatorAllPlaces";
    }

    @GetMapping("places/add")
    public String getPlacesAddingPage(Model model) {
        this.setModeratorData(model);

        List<String> categories =
                this.categoryService.getAll().
                        stream().map(Category::getName).toList();

        model.addAttribute("categories", categories);

        return "moderator/managePlaces";
    }

    @PostMapping("places/add")
    public String addNewActivity(@RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("latitude") Double latitude,
                                 @RequestParam("longitude") Double longitude,
                                 @RequestParam("categoryList") String category,
                                 @RequestParam("time") String time,
                                 @RequestParam("price") Double price) {


        Optional<User> user = this.userService.getByLogin(sessionContext.getUserLogin());
        user.ifPresent(value -> this.activityService.addActivity(name, description, value.getCity().getName(),
                category, latitude, longitude, time, price, new byte[]{0}));

        return "redirect:/moderator/places";
    }

    @GetMapping("places/change")
    public String getPlacesManagementPage(Model model) {
        sessionContext.addUserNameToPage(model);
        User user = this.userService.getByLogin(sessionContext.getUserLogin()).get();
        List<Activity> activities = this.activityService.getByCity(user.getCity().getName());
        model.addAttribute("activities", activities);
        return "moderator/changePlaces";
    }

    @GetMapping("places/change/{activityId}")
    public String getPlaceUpdatePage(@PathVariable("activityId") Long id, Model model) {
        sessionContext.addUserNameToPage(model);
        Activity activity = this.activityService.getById(id);
        model.addAttribute("placeId", activity.getId());
        model.addAttribute("name", activity.getName());
        model.addAttribute("description", activity.getDescription());
        model.addAttribute("time", activity.getDuration());
        model.addAttribute("price", activity.getPrice());
        return "moderator/updatePlace";
    }

    @PostMapping("/places/update/{id}")
    public String updatePlace(@PathVariable("id") Long placeID,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("time") String time,
                              @RequestParam("price") Double price) {

        this.activityService.update(placeID, name, description, time, price);
        return "redirect:/moderator/places/change";
    }

    @PostMapping("/places/delete/{id}")
    public String deletePlace(@PathVariable("id") Long placeId) {
        this.activityService.delete(placeId);
        return "redirect:/moderator/places/change";
    }

    @GetMapping("/orgs")
    public String getOrganizations(Model model) {
        this.setModeratorData(model);
        model.addAttribute("agencies", this.userService.getAllAgencies());
        model.addAttribute("orgs", this.userService.getAllOrganizations());
        return "moderator/moderatorUsers";
    }

    @PostMapping("/orgs")
    public String addOrganization(@RequestParam("orgName") String name,
                                  @RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  @RequestParam("roleName") String roleName) {

        if (roleName.equals(MODEL_AGENCY_NAME)) {
            roleName = DB_AGENCY_NAME;
        }
        Optional<User> user = this.userService.getByLogin(sessionContext.getUserLogin());

        if (user.isPresent()) {
            this.userService.saveOrganization(login, password, roleName, user.get().getCity().getName(), name);
        }

        return "redirect:/moderator/orgs";
    }

    @PostMapping("/orgs/{login}")
    public String changeStatus(@PathVariable("login") String login) {
        this.userService.changeStatus(login);
        return "redirect:/moderator/orgs";
    }

    private void setModeratorData(Model model) {
        sessionContext.addUserNameToPage(model);
        User user = this.userService.getByLogin(sessionContext.getUserLogin()).get();
        model.addAttribute("city", user.getCity().getName());
    }
}
