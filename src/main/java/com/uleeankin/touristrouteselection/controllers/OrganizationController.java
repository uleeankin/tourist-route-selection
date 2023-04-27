package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.event.EventService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    private final ActivityService activityService;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public OrganizationController(ActivityService activityService,
                                  EventService eventService,
                                  UserService userService) {
        this.activityService = activityService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String getEventAddingPage(Model model) {
        SessionContext.addUserNameToPage(model);
        return "orgs/addingEvents";
    }

    @PostMapping("/add")
    public String addNewEvent(
            @RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
            @RequestParam("categoryList") String categoryName, @RequestParam("price") Double price,
            @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
            @RequestParam("time") String duration, @RequestParam("breakTime") String breakTime,
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {

        Optional<User> user = this.userService.getByLogin(SessionContext.getUserLogin());
        user.ifPresent(value -> this.activityService.addActivity(name, description, value.getCity().getName(),
                categoryName, latitude, longitude, duration, price, new byte[]{0}));

        this.eventService.save(
                this.activityService.getActivity(
                        name, latitude, longitude).getId(), startDate, endDate,
                SessionContext.getUserLogin(), breakTime, startTime, endTime, duration);

        return "redirect:/organization/add";
    }
}
