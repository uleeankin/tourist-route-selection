package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventSession;
import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.activity.service.ActivityService;
import com.uleeankin.touristrouteselection.activity.attributes.category.service.CategoryService;
import com.uleeankin.touristrouteselection.city.service.CityService;
import com.uleeankin.touristrouteselection.activity.attributes.event.service.EventService;
import com.uleeankin.touristrouteselection.activity.attributes.feedback.service.ActivityFeedbackService;
import com.uleeankin.touristrouteselection.route.service.RouteService;
import com.uleeankin.touristrouteselection.user.service.UserService;
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
            this.sessionContext.setCityValueToSession(request,
                    user.getUser().getCity().getName());
        }
    }

    private void setSessionInitialCategoryValue(HttpSession session,
                                                HttpServletRequest request) {
        if (this.sessionContext.getCurrentCategory(session).isEmpty()) {
            this.sessionContext.setCategoryValueToSession(request,
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

        model.addAttribute("events",
                this.eventService.getFavourites(
                        this.sessionContext.getUserLogin()));

        model.addAttribute("routes",
                this.routeService.getFavourites(
                        sessionContext.getUserLogin()));

        return "tourist/favouritePlaces";
    }

    @GetMapping("/place/{id}")
    public String getActivityDetails(@PathVariable("id") Long id, Model model) {
        this.setActivityDetails(this.activityService.getById(id), model);
        return "tourist/placeDetails";
    }

    @GetMapping("/event/{id}")
    public String getEventDetails(@PathVariable("id") Long id, Model model) {
        Event event = this.eventService.getById(id);
        this.setActivityDetails(event.getActivity(), model);
        model.addAttribute("dates",
                event.getStartDate() + " - " + event.getEndDate());
        model.addAttribute("times",
                this.eventService.getSchedule(id)
                        .stream()
                        .map(EventSession::getSessionTime)
                        .toList());
        return "tourist/eventDetails";
    }

    private void setActivityDetails(Activity activity, Model model) {
        sessionContext.addUserNameToPage(model);
        model.addAttribute("placeName", activity.getName());
        model.addAttribute("activityId", activity.getId());

        model.addAttribute("category", activity.getCategory().getName());
        model.addAttribute("city", activity.getCoordinate().getCity().getName());
        model.addAttribute("description", activity.getDescription());
        model.addAttribute("price", activity.getPrice() + " руб.");
        model.addAttribute("time",
                "Длительность: " + activity.getDuration());
        model.addAttribute("feedbacks",
                this.activityFeedbackService.getAll(activity.getId()));
        model.addAttribute("commentNumber",
                this.activityFeedbackService.getFeedbackNumber(activity.getId()));

        model.addAttribute("averageAssessment",
                this.activityFeedbackService.getAverageAssessment(activity.getId()));

        model.addAttribute("favourite",
                this.activityService.isExists(sessionContext.getUserLogin(),
                        activity.getId()));
    }

    @PostMapping("/feedback/place/{id}")
    public String addPlaceFeedback(@PathVariable("id") Long activityId,
                              @RequestParam("comment") String comment,
                              @RequestParam("assessment") Integer assessment) {

        this.addFeedback(activityId, comment, assessment);
        return "redirect:/tourist/place/{id}";
    }

    @PostMapping("/feedback/event/{id}")
    public String addEventFeedback(@PathVariable("id") Long activityId,
                                   @RequestParam("comment") String comment,
                                   @RequestParam("assessment") Integer assessment) {

        this.addFeedback(activityId, comment, assessment);
        return "redirect:/tourist/event/{id}";
    }

    private void addFeedback(Long activityId, String comment, Integer assessment) {
        this.activityFeedbackService.addFeedback(sessionContext.getUserLogin(),
                activityId, assessment, comment);
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
