package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.Event;
import com.uleeankin.touristrouteselection.models.activity.EventSession;
import com.uleeankin.touristrouteselection.models.user.User;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.event.EventService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    private final ActivityService activityService;
    private final EventService eventService;
    private final UserService userService;
    private final SessionContext sessionContext;

    @Autowired
    public OrganizationController(ActivityService activityService,
                                  EventService eventService,
                                  UserService userService, SessionContext sessionContext) {
        this.activityService = activityService;
        this.eventService = eventService;
        this.userService = userService;
        this.sessionContext = sessionContext;
    }

    @GetMapping("/events")
    public String showAllEvents(Model model) {
        sessionContext.addUserNameToPage(model);
        model.addAttribute("events",
                this.eventService.getAll());
        return "orgs/all";
    }

    @GetMapping("/add")
    public String getEventAddingPage(Model model) {
        sessionContext.addUserNameToPage(model);
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

        Optional<User> user = this.userService.getByLogin(sessionContext.getUserLogin());
        user.ifPresent(value -> this.activityService.addActivity(name, description, value.getCity().getName(),
                categoryName, latitude, longitude, duration, price, new byte[]{0}));

        this.eventService.save(
                this.activityService.getActivity(
                        name, latitude, longitude).getId(), startDate, endDate,
                sessionContext.getUserLogin(), breakTime, startTime, endTime, duration);

        return "redirect:/organization/add";
    }

    @GetMapping("/manage")
    public String showManageEventsPage(Model model) {
        sessionContext.addUserNameToPage(model);
        model.addAttribute("events",
                this.eventService.getAll());
        return "orgs/manage";
    }

    @GetMapping("/event/change/{eventId}")
    public String getEventUpdatePage(@PathVariable("eventId") Long id, Model model) {
        sessionContext.addUserNameToPage(model);
        Event event = this.eventService.getById(id);
        model.addAttribute("eventId", event.getActivity().getId());
        model.addAttribute("name", event.getActivity().getName());
        model.addAttribute("description", event.getActivity().getDescription());
        model.addAttribute("time", event.getActivity().getDuration());
        model.addAttribute("price", event.getActivity().getPrice());
        model.addAttribute("startDate", event.getStartDate());
        model.addAttribute("endDate", event.getEndDate());
        return "orgs/update";
    }

    @PostMapping("/event/update/{eventId}")
    public String updatePlace(@PathVariable("eventId") Long eventId,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("time") String time,
                              @RequestParam("price") Double price,
                              @RequestParam("startDate") String startDate,
                              @RequestParam("endDate") String endDate) {

        this.eventService.update(eventId, name, description, time, price, startDate, endDate);
        return "redirect:/organization/manage";
    }

    @GetMapping("/event/delete/{eventId}")
    public String deleteEvent(@PathVariable("eventId") Long id) {
        this.eventService.delete(id);
        return "redirect:/organization/manage";
    }

    @GetMapping("/sessions/{id}")
    public String getSchedulePage(@PathVariable("id") Long id, Model model) {
        sessionContext.addUserNameToPage(model);
        List<EventSession> sessions = this.eventService.getSchedule(id);
        Activity activity = this.activityService.getById(id);
        model.addAttribute("name", activity.getName());
        model.addAttribute("eventId", activity.getId());
        model.addAttribute("sessions", sessions);
        return "orgs/schedule";
    }

    @PostMapping("/session/delete/{id}/{time}")
    public String deleteSession(@PathVariable("id") Long id,
                                @PathVariable("time") String time) {
        this.eventService.deleteSession(id, time);
        return "redirect:/organization/sessions/{id}";
    }

    @PostMapping("/session/add/{id}")
    public String addSession(@PathVariable("id") Long id,
                                @RequestParam("startTime") String time) {
        this.eventService.addSession(id, time);
        return "redirect:/organization/sessions/{id}";
    }

    @PostMapping("/session/back")
    public String backToManagePage() {
        return "redirect:/organization/manage";
    }
}
