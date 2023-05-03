package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.ActivityFeedback;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.models.activity.Event;
import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.category.CategoryService;
import com.uleeankin.touristrouteselection.services.city.CityService;
import com.uleeankin.touristrouteselection.services.event.EventService;
import com.uleeankin.touristrouteselection.services.feedback.ActivityFeedbackService;
import com.uleeankin.touristrouteselection.services.route.RouteService;
import com.uleeankin.touristrouteselection.services.user.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/tourist")
public class TouristController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final CityService cityService;

    private final ActivityService activityService;

    private final ActivityFeedbackService activityFeedbackService;

    private final RouteService routeService;
    private final EventService eventService;
    private final SessionContext sessionContext;


    @Autowired
    public TouristController(UserService userService,
                             CategoryService categoryService,
                             CityService cityService,
                             ActivityService activityService,
                             ActivityFeedbackService activityFeedbackService,
                             RouteService routeService,
                             EventService eventService,
                             SessionContext sessionContext) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.activityService = activityService;
        this.activityFeedbackService = activityFeedbackService;
        this.routeService = routeService;
        this.eventService = eventService;
        this.sessionContext = sessionContext;
    }


    @GetMapping
    public String getMainPage(Model model, HttpServletRequest request,
                              HttpSession session) {
        sessionContext.addUserNameToPage(model);

        Tourist user = this.userService
                .getTouristByLogin(sessionContext.getUserLogin());

        this.setInitialSessionAttributes(session, request, user);

        model.addAttribute("name",
                user.getSurname() + " " + user.getName());

        model.addAttribute("city", user.getUser().getCity().getName());

        model.addAttribute("routes",
                this.routeService.getByOwner(user.getUser().getLogin()));

        return "tourist/touristMain";
    }

    private void setInitialSessionAttributes(HttpSession session,
                                             HttpServletRequest request,
                                             Tourist user) {

        this.setSessionInitialCityValue(session, request, user);
        this.setSessionInitialCategoryValue(session, request);
        this.setSessionInitialDateValue(session, request);
    }

    private void setSessionInitialCityValue(HttpSession session,
                                            HttpServletRequest request,
                                            Tourist user) {
        if (this.sessionContext.getCurrentCity(session).isEmpty()) {
            sessionContext.setCityValueToSession(request,
                    user.getUser().getCity().getName());
        }
    }

    private void setSessionInitialCategoryValue(HttpSession session,
                                                HttpServletRequest request) {
        if (this.sessionContext.getCurrentCategory(session).isEmpty()) {
            sessionContext.setCategoryValueToSession(request,
                    this.categoryService.getAll().stream()
                            .map(Category::getName)
                            .toList().get(0));
        }
    }

    private void setSessionInitialDateValue(HttpSession session,
                                            HttpServletRequest request) {
        if (this.sessionContext
                .getSessionDateAttribute(session).isEmpty()) {
            this.sessionContext.setSessionDateAttribute(request,
                    new Date(new java.util.Date().getTime()).toString());
        }
    }

    @GetMapping("/characteristics")
    public String getPlaceMenuPage(Model model) {

        model.addAttribute("cities",
                this.cityService.getAll().stream().map(City::getName).toList());

        model.addAttribute("categories",
                this.categoryService.getAll().
                    stream().map(Category::getName).toList());

        return "/tourist/characteristics";
    }

    @PostMapping("/characteristics")
    public String applyCharacteristics(@RequestParam("cityname") String city,
                                       @RequestParam("categoryList") String category,
                                       HttpServletRequest request) {

        this.sessionContext.setCityValueToSession(request, city);
        this.sessionContext.setCategoryValueToSession(request, category);

        return "redirect:/tourist/places";
    }

    @GetMapping("/events/characteristics")
    public String getEventMenuPage(Model model) {
        model.addAttribute("cities", this.cityService.getAll()
                .stream().map(City::getName).toList());
        return "/tourist/eventsCharacteristics";
    }

    @PostMapping("/events/characteristics")
    public String applyEventCharacteristics(@RequestParam("cityname") String city,
                                       @RequestParam("date") String date,
                                       HttpServletRequest request) {

        this.sessionContext.setCityValueToSession(request, city);
        this.sessionContext.setSessionDateAttribute(request, date);

        return "redirect:/tourist/events";
    }

    @GetMapping("/places")
    public String getPlaces(Model model, HttpSession session) {
        sessionContext.addUserNameToPage(model);

        String city = sessionContext.getCurrentCity(session);
        String category = sessionContext.getCurrentCategory(session);
        model.addAttribute("city", city);
        model.addAttribute("category", category);

        List<Activity> activities =
                this.activityService.getByCityAndCategory(city, category);

        model.addAttribute("activities", activities);

        return "tourist/touristPlaces";
    }

    @GetMapping("/events")
    public String getEvents(Model model, HttpSession session) {
        this.sessionContext.addUserNameToPage(model);

        model.addAttribute("city",
                this.sessionContext.getCurrentCity(session));
        model.addAttribute("currentDate",
                this.sessionContext.getSessionDateAttribute(session));

        model.addAttribute("events",
                this.eventService.getByDate(
                    this.sessionContext
                            .getSessionDateAttribute(session)));

        return "tourist/touristEvents";
    }

    @PostMapping("/favourites/{id}")
    public String addToFavourites(@PathVariable("id") Long id) {

        this.activityService.addToFavourites(sessionContext.getUserLogin(),
                id);

        return "redirect:/tourist/place/{id}";
    }

    @PostMapping("/favourites/delete/{id}")
    public String deleteFromFavourites(@PathVariable("id") Long id) {

        this.activityService.deleteFromFavourites(sessionContext.getUserLogin(),
                id);

        return "redirect:/tourist/place/{id}";
    }

    @GetMapping("/favourites")
    public String favouritesPage(Model model) {
        sessionContext.addUserNameToPage(model);

        model.addAttribute("activities",
                this.activityService.getFavourites(
                        sessionContext.getUserLogin()));

        return "tourist/favouritePlaces";
    }

    @GetMapping("/place/{id}")
    public String getActivityDetails(@PathVariable("id") Long id, Model model) {
        sessionContext.addUserNameToPage(model);
        Activity activity = this.activityService.getById(id);
        model.addAttribute("placeName", activity.getName());
        model.addAttribute("activityId", activity.getId());

        model.addAttribute("category", activity.getCategory().getName());
        model.addAttribute("description", activity.getDescription());
        List<ActivityFeedback> feedback =
                this.activityFeedbackService.getAll(id);
        model.addAttribute("feedbacks", feedback);
        model.addAttribute("commentNumber",
                this.activityFeedbackService.getFeedbackNumber(id));

        model.addAttribute("averageAssessment",
                this.activityFeedbackService.getAverageAssessment(id));

        model.addAttribute("favourite",
                this.activityService.isExists(sessionContext.getUserLogin(), id));

        return "tourist/placeDetails";
    }

    @PostMapping("/feedback/{id}")
    public String addFeedback(@PathVariable("id") Long activityId,
                              @RequestParam("comment") String comment,
                              @RequestParam("assessment") Integer assessment) {

        this.activityFeedbackService.addFeedback(sessionContext.getUserLogin(),
                activityId, assessment, comment);

        return "redirect:/tourist/place/{id}";
    }

    @GetMapping("/routes")
    public String getRoutesPage(Model model, HttpSession session) {
        sessionContext.addUserNameToPage(model);
        model.addAttribute("city",
                sessionContext.getCurrentCity(session));
        model.addAttribute("routes",
                this.routeService.getAllWithoutOwn(
                        sessionContext.getUserLogin()));
        return "tourist/touristRoutes";
    }
}
