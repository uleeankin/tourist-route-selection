package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.algorithm.RouteCreator;
import com.uleeankin.touristrouteselection.models.City;
import com.uleeankin.touristrouteselection.models.route.CompletedRoute;
import com.uleeankin.touristrouteselection.models.route.Route;
import com.uleeankin.touristrouteselection.models.activity.Activity;
import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.models.activity.ActivityStatus;
import com.uleeankin.touristrouteselection.models.route.RouteFeedback;
import com.uleeankin.touristrouteselection.services.activity.ActivityService;
import com.uleeankin.touristrouteselection.services.category.CategoryService;
import com.uleeankin.touristrouteselection.services.city.CityService;
import com.uleeankin.touristrouteselection.services.feedback.RouteFeedbackService;
import com.uleeankin.touristrouteselection.services.route.RouteService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController {

    private final CityService cityService;

    private final CategoryService categoryService;

    private final ActivityService activityService;

    private final RouteService routeService;

    private final RouteFeedbackService routeFeedbackService;

    private String name;

    private String description;

    private String city;

    private String category;

    private final List<Activity> addedActivities;

    @Autowired
    public RouteController(CityService cityService,
                           CategoryService categoryService,
                           ActivityService activityService,
                           RouteService routeService,
                           RouteFeedbackService routeFeedbackService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.addedActivities = new ArrayList<>();
        this.routeService = routeService;
        this.routeFeedbackService = routeFeedbackService;
    }

    @GetMapping
    public String getStartCreatingRoutePage(Model model) {
        SessionContext.addUserNameToPage(model);
        model.addAttribute("cities",
                this.cityService.getAll().stream()
                        .map(City::getName).toList());
        return "route/routeCharacteristics";
    }

    @PostMapping
    public String addRouteCharacteristics(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("cityname") String city) {

        this.name = name;
        this.description = description;
        this.city = city;

        return "redirect:/route/characteristics";
    }

    @GetMapping("/characteristics")
    public String chooseCategory(Model model) {

        List<String> categories = this.categoryService.getAll()
                .stream().map(Category::getName).toList();
        model.addAttribute("categories",
                categories);

        return "route/categoryCharacteristics";
    }

    @PostMapping("/characteristics")
    public String applyCategory(@RequestParam("categoryValue") String category) {
        this.category = category;
        return "redirect:/route/places";
    }

    @GetMapping("/places")
    public String getPlacesPage(Model model) {
        SessionContext.addUserNameToPage(model);
        model.addAttribute("category", this.category);

        List<Activity> activities = this.activityService
                .getFavouritesByCityAndCategory(
                SessionContext.getUserLogin(), this.city, this.category);
        model.addAttribute("activities",
                getActivities(activities));
        return "route/placesForRoutePage";
    }

    private List<ActivityStatus> getActivities(List<Activity> activities) {

        List<ActivityStatus> activityStatuses = new ArrayList<>();

        for (Activity activity : activities) {
            activityStatuses.add(new ActivityStatus(activity,
                    !this.addedActivities.stream()
                            .map(Activity::getId).toList()
                            .contains(activity.getId())));
        }

        return activityStatuses;
    }

    @PostMapping("/places/add/{id}")
    public String addPlaceToRoute(@PathVariable("id") Long id) {
        this.addedActivities.add(this.activityService.getById(id));
        return "redirect:/route/places";
    }

    @PostMapping("/places/delete/{id}")
    public String deletePlaceFromRoute(@PathVariable("id") Long id) {
        this.addedActivities.remove(this.activityService.getById(id));
        return "redirect:/route/places";
    }

    @PostMapping("/create")
    public String createRoute() {
        List<Activity> route = new RouteCreator().createNewRoute(this.addedActivities);
        this.routeService.save(this.name, this.description,
                SessionContext.getUserLogin(), this.city, route);

        return "redirect:/route/create";
    }

    @GetMapping("/create")
    public String showCreatedRoute(Model model) {
        SessionContext.addUserNameToPage(model);

        Route route = this.routeService.getByOwnerAndName(
                SessionContext.getUserLogin(), this.name);
        model.addAttribute("name", route.getName());
        this.addRouteDetailsToModel(model, route);

        List<Activity> activities = this.routeService.getRouteActivities(route.getId());
        model.addAttribute("activities", activities);

        return "route/detailedRoutePage";
    }

    @GetMapping("/owner/{id}")
    public String getOwnerRouteDetails(@PathVariable("id") Long routeId,  Model model) {
        SessionContext.addUserNameToPage(model);
        Route route = this.routeService.getById(routeId);

        model.addAttribute("owner", route.getOwner());
        model.addAttribute("routeName", route.getName());
        model.addAttribute("routeStatus", route.getStatus());
        this.addRouteDetailsToModel(model, route);
        model.addAttribute("routeId", routeId);
        model.addAttribute("usesNumber",
                this.routeService.getRouteUsesNumber(routeId));

        List<Activity> activities = this.routeService.getRouteActivities(route.getId());
        model.addAttribute("activities", activities);

        this.addFeedback(model, routeId);

        return "tourist/ownerRouteDetails";
    }

    @GetMapping("/{id}")
    public String getRouteDetails(@PathVariable("id") Long routeId,  Model model) {
        SessionContext.addUserNameToPage(model);
        Route route = this.routeService.getById(routeId);

        model.addAttribute("owner", route.getOwner());
        model.addAttribute("routeName", route.getName());
        model.addAttribute("routeId", route.getId());
        this.addRouteDetailsToModel(model, route);

        List<Activity> activities = this.routeService.getRouteActivities(route.getId());
        model.addAttribute("activities", activities);

        this.addFeedback(model, routeId);

        model.addAttribute("favourite",
                this.routeService.isFavourite(SessionContext.getUserLogin(), routeId));

        model.addAttribute("completed",
                this.routeService.isCompleted(SessionContext.getUserLogin(), routeId));

        return "tourist/routeDetails";
    }

    @PostMapping("/status/{id}")
    public String changeRouteStatus(@PathVariable("id") Long routeId) {
        this.routeService.changeStatus(routeId);
        return "redirect:/route/owner/{id}";
    }

    @PostMapping("/feedback/{id}")
    public String addFeedbackToRoute(@PathVariable("id") Long routeId,
                              @RequestParam("comment") String comment,
                              @RequestParam("assessment") Integer assessment) {

        this.routeFeedbackService.addFeedback(SessionContext.getUserLogin(),
                routeId, assessment, comment);

        return "redirect:/route/{id}";
    }

    @PostMapping("/favourites/add/{id}")
    public String addToFavourites(@PathVariable("id") Long id) {

        this.routeService.addToFavourites(SessionContext.getUserLogin(),
                id);

        return "redirect:/route/{id}";
    }

    @GetMapping("/favourites")
    public String getFavouritesPage(Model model) {

        SessionContext.addUserNameToPage(model);

        List<Route> routes =
                this.routeService.getFavourites(SessionContext.getUserLogin());

        model.addAttribute("routes", routes);

        return "tourist/favouriteRoutes";
    }

    @GetMapping("/completed")
    public String getCompletedRoutesPage(Model model) {

        SessionContext.addUserNameToPage(model);

        List<CompletedRoute> routes =
                this.routeService.getCompleted(SessionContext.getUserLogin());

        model.addAttribute("routes", routes);

        return "tourist/completedRoutes";
    }

    @PostMapping("/complete/{id}")
    public String completeRoute(@PathVariable("id") Long routeId) {

        this.routeService.completeRoute(SessionContext.getUserLogin(), routeId);

        return "redirect:/route/{id}";
    }

    @PostMapping("/favourites/delete/{id}")
    public String deleteFromFavourites(@PathVariable("id") Long id) {

        this.routeService.deleteFromFavourites(SessionContext.getUserLogin(),
                id);

        return "redirect:/route/{id}";
    }

    private void addFeedback(Model model, Long routeId) {
        List<RouteFeedback> feedback =
                this.routeFeedbackService.getAll(routeId);
        model.addAttribute("feedbacks", feedback);
        model.addAttribute("commentNumber",
                this.routeFeedbackService.getFeedbackNumber(routeId));

        model.addAttribute("averageAssessment",
                this.routeFeedbackService.getAverageAssessment(routeId));
    }

    private void addRouteDetailsToModel(Model model, Route route) {
        model.addAttribute("city", route.getCity().getName());
        model.addAttribute("price", route.getPrice());
        model.addAttribute("time", route.getTime());
        model.addAttribute("length", route.getLength());
        model.addAttribute("description", route.getDescription());
    }

}
