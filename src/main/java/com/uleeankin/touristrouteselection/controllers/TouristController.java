package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.ActivityFeedback;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.models.user.Tourist;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.category.CategoryService;
import com.uleeankin.touristrouteselection.services.city.CityService;
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

import java.util.List;

@Controller
@RequestMapping("/tourist")
public class TouristController {

    private static final String SESSION_CITY_ATTRIBUTE = "currentCity";
    private static final String SESSION_CATEGORY_ATTRIBUTE = "currentCategory";

    private final UserService userService;

    private final CategoryService categoryService;

    private final CityService cityService;

    private final ActivityService activityService;

    private final ActivityFeedbackService activityFeedbackService;

    private final RouteService routeService;


    @Autowired
    public TouristController(UserService userService,
                             CategoryService categoryService,
                             CityService cityService,
                             ActivityService activityService,
                             ActivityFeedbackService activityFeedbackService,
                             RouteService routeService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.activityService = activityService;
        this.activityFeedbackService = activityFeedbackService;
        this.routeService = routeService;
    }


    @GetMapping
    public String getMainPage(Model model, HttpServletRequest request) {
        SessionContext.addUserNameToPage(model);

        Tourist user = this.userService
                .getTouristByLogin(SessionContext.getUserLogin());

        model.addAttribute("name",
                user.getSurname() + " " + user.getName());

        model.addAttribute("city", user.getUser().getCity().getName());

        model.addAttribute("routes",
                this.routeService.getByOwner(user.getUser().getLogin()));

        this.setCityValueToSession(request,
                user.getUser().getCity().getName());
        this.setCategoryValueToSession(request,
                this.categoryService.getAll().stream()
                        .map(Category::getName)
                        .toList().get(0));

        return "tourist/touristMain";
    }

    @GetMapping("/characteristics")
    public String getMainMenuPage(Model model) {

        List<String> cities = this.cityService.getAll().stream().map(City::getName).toList();
        model.addAttribute("cities", cities);

        List<String> categories =
                this.categoryService.getAll().
                        stream().map(Category::getName).toList();
        model.addAttribute("categories", categories);

        return "/tourist/characteristics";
    }

    @PostMapping("/characteristics")
    public String applyCharacteristics(@RequestParam("cityname") String city,
                                       @RequestParam("categoryList") String category,
                                       HttpServletRequest request) {

        this.setCityValueToSession(request, city);
        this.setCategoryValueToSession(request, category);

        return "redirect:/tourist/places";
    }

    @GetMapping("/places")
    public String getPlaces(Model model, HttpSession session) {
        SessionContext.addUserNameToPage(model);

        String city = this.getCurrentCity(session);
        String category = this.getCurrentCategory(session);
        model.addAttribute("city", city);
        model.addAttribute("category", category);

        List<Activity> activities =
                this.activityService.getByCityAndCategory(city, category);

        model.addAttribute("activities", activities);

        return "tourist/touristPlaces";
    }

    @GetMapping("/events")
    public String getEvents(Model model) {
        SessionContext.addUserNameToPage(model);

        Tourist user = this.userService
                .getTouristByLogin(SessionContext.getUserLogin());

        String city = user.getUser().getCity().getName();
        String category = this.categoryService.getAll().
                stream().map(Category::getName).toList().get(0);
        model.addAttribute("city", city);
        model.addAttribute("category", category);

        List<Activity> activities =
                this.activityService.getByCityAndCategory(city, category);

        model.addAttribute("activities", activities);

        return "tourist/touristEvents";
    }

    @PostMapping("/favourites/{id}")
    public String addToFavourites(@PathVariable("id") Long id) {

        this.activityService.addToFavourites(SessionContext.getUserLogin(),
                id);

        return "redirect:/tourist/place/{id}";
    }

    @PostMapping("/favourites/delete/{id}")
    public String deleteFromFavourites(@PathVariable("id") Long id) {

        this.activityService.deleteFromFavourites(SessionContext.getUserLogin(),
                id);

        return "redirect:/tourist/place/{id}";
    }

    @GetMapping("/favourites")
    public String favouritesPage(Model model) {
        SessionContext.addUserNameToPage(model);

        List<Activity> activities =
                this.activityService.getFavourites(SessionContext.getUserLogin());

        model.addAttribute("activities", activities);

        return "tourist/favouritePlaces";
    }

    @GetMapping("/place/{id}")
    public String getActivityDetails(@PathVariable("id") Long id, Model model) {
        SessionContext.addUserNameToPage(model);
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
                this.activityService.isExists(SessionContext.getUserLogin(), id));

        return "tourist/placeDetails";
    }

    @PostMapping("/feedback/{id}")
    public String addFeedback(@PathVariable("id") Long activityId,
                              @RequestParam("comment") String comment,
                              @RequestParam("assessment") Integer assessment) {

        this.activityFeedbackService.addFeedback(SessionContext.getUserLogin(),
                activityId, assessment, comment);

        return "redirect:/tourist/place/{id}";
    }

    @GetMapping("/routes")
    public String getRoutesPage(Model model, HttpSession session) {
        SessionContext.addUserNameToPage(model);
        model.addAttribute("city", this.getCurrentCity(session));
        model.addAttribute("routes",
                this.routeService.getAllWithoutOwn(SessionContext.getUserLogin()));
        return "tourist/touristRoutes";
    }

    private void setCityValueToSession(HttpServletRequest request, String cityName) {
        request.getSession().setAttribute(SESSION_CITY_ATTRIBUTE, cityName);
    }

    private void setCategoryValueToSession(HttpServletRequest request, String categoryName) {
        request.getSession().setAttribute(SESSION_CATEGORY_ATTRIBUTE, categoryName);
    }

    private String getCurrentCity(HttpSession session) {
        return session.getAttribute(SESSION_CITY_ATTRIBUTE).toString();
    }

    private String getCurrentCategory(HttpSession session) {
        return session.getAttribute(SESSION_CATEGORY_ATTRIBUTE).toString();
    }
}
