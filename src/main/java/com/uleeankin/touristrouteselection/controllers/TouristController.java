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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private String currentCity;

    private String currentCategory;

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
    public String getMainPage(Model model) {
        SessionContext.addUserNameToPage(model);

        Tourist user = this.userService
                .getTouristByLogin(SessionContext.getUserLogin());

        model.addAttribute("name",
                user.getSurname() + " " + user.getName());

        model.addAttribute("city", user.getUser().getCity().getName());

        model.addAttribute("routes",
                this.routeService.getByOwner(user.getUser().getLogin()));

        this.currentCity = user.getUser().getCity().getName();
        this.currentCategory = this.categoryService.getAll().
                stream().map(Category::getName).toList().get(0);

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
                                       @RequestParam("categoryList") String category) {

        this.currentCity = city;
        this.currentCategory = category;

        return "redirect:/tourist/places";
    }

    @GetMapping("/places")
    public String getPlaces(Model model) {
        SessionContext.addUserNameToPage(model);
        model.addAttribute("city", this.currentCity);
        model.addAttribute("category", this.currentCategory);

        List<Activity> activities =
                this.activityService.getByCityAndCategory(this.currentCity,
                        this.currentCategory);

        model.addAttribute("activities", activities);

        return "tourist/touristPlaces";
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
        model.addAttribute("city", activity.getCity().getName());
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
    public String getRoutesPage(Model model) {
        SessionContext.addUserNameToPage(model);
        model.addAttribute("city", this.currentCity);
        model.addAttribute("routes",
                this.routeService.getAllWithoutOwn(SessionContext.getUserLogin()));
        return "tourist/touristRoutes";
    }
}
