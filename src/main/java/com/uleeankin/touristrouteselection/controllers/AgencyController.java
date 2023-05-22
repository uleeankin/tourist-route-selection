package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.attributes.category.service.CategoryService;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.service.PreliminaryActivityService;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.model.ActivityStatus;
import com.uleeankin.touristrouteselection.activity.service.ActivityService;
import com.uleeankin.touristrouteselection.algorithm.RouteCreator;
import com.uleeankin.touristrouteselection.route.model.CreatedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.route.service.RouteService;
import com.uleeankin.touristrouteselection.user.model.User;
import com.uleeankin.touristrouteselection.user.service.UserService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agency")
public class AgencyController {

    private static final String NAME_ATTRIBUTE = "routeName";
    private static final String DESCRIPTION_ATTRIBUTE = "routeDescription";
    private static final String PRICE_ATTRIBUTE = "routePrice";
    private static final String TOURISTS_ATTRIBUTE = "routeMaxTouristsNumber";
    private static final String START_DATE_ATTRIBUTE = "routeStartDate";
    private static final String END_DATE_ATTRIBUTE = "routeEndDate";

    private final SessionContext sessionContext;
    private final ActivityService activityService;
    private final PreliminaryActivityService preliminaryActivityService;
    private final CategoryService categoryService;
    private final RouteService routeService;
    private final UserService userService;

    @Autowired
    public AgencyController(SessionContext sessionContext,
                            ActivityService activityService,
                            PreliminaryActivityService preliminaryActivityService,
                            CategoryService categoryService,
                            RouteService routeService,
                            UserService userService) {
        this.sessionContext = sessionContext;
        this.activityService = activityService;
        this.preliminaryActivityService = preliminaryActivityService;
        this.categoryService = categoryService;
        this.routeService = routeService;
        this.userService = userService;
    }

    @GetMapping
    public String getMainPage(Model model, HttpSession session,
                              HttpServletRequest request) {
        this.sessionContext.addUserNameToPage(model);
        this.setInitialSessionAttributes(session, request,
                this.userService.getByLogin(this.sessionContext.getUserLogin()).get());
        return "agency/main";
    }

    private void setInitialSessionAttributes(HttpSession session,
                                             HttpServletRequest request,
                                             User user) {

        this.setSessionInitialCityValue(session, request, user);
        this.setSessionInitialCategoryValue(session, request);
    }

    private void setSessionInitialCityValue(HttpSession session,
                                            HttpServletRequest request,
                                            User user) {
        if (this.sessionContext.getCurrentCity(session).isEmpty()) {
            this.sessionContext.setCityValueToSession(request,
                    user.getCity().getName());
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

    @GetMapping("/all")
    public String getAllRoutes(Model model) {
        this.sessionContext.addUserNameToPage(model);
        model.addAttribute("routes",
                this.routeService.getByOwner(this.sessionContext.getUserLogin()));
        return "agency/allRoutes";
    }

    @GetMapping("/add")
    public String getNewRouteStartPage(Model model) {
        this.sessionContext.addUserNameToPage(model);
        return "agency/addRoute";
    }

    @PostMapping("/route/characteristics")
    public String addRouteCharacteristics(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("price") Double price,
                                          @RequestParam("maxTouristsNumber") Integer maxTouristsNumber,
                                          @RequestParam("startDate") String startDate,
                                          @RequestParam("endDate") String endDate,
                                          HttpServletRequest request) {

        this.setRouteCharacteristics(name, description, price,
                maxTouristsNumber, startDate, endDate, request);

        return "redirect:/agency/route/places";
    }

    private void setRouteCharacteristics(String name, String description,
                                         Double price, Integer maxTouristsNumber,
                                         String startDate, String endDate,
                                         HttpServletRequest request) {

        request.getSession().setAttribute(NAME_ATTRIBUTE, name);
        request.getSession().setAttribute(DESCRIPTION_ATTRIBUTE, description);
        request.getSession().setAttribute(PRICE_ATTRIBUTE, price);
        request.getSession().setAttribute(TOURISTS_ATTRIBUTE, maxTouristsNumber);
        request.getSession().setAttribute(START_DATE_ATTRIBUTE, startDate);
        request.getSession().setAttribute(END_DATE_ATTRIBUTE, endDate);
    }

    @GetMapping("/route/places")
    public String getPlacesForRoutePage(Model model, HttpSession session) {
        this.sessionContext.addUserNameToPage(model);

        model.addAttribute("category",
                this.sessionContext.getCurrentCategory(session));

        List<Activity> activities = this.activityService
                .getByCityAndCategory(
                        this.sessionContext.getCurrentCity(session),
                        this.sessionContext.getCurrentCategory(session));
        model.addAttribute("activities",
                getActivities(activities, session));
        return "agency/placesForRoutePage";
    }

    private List<ActivityStatus> getActivities(List<Activity> activities,
                                               HttpSession session) {

        return this.activityService.getActivityStatuses(
                activities, session, this.preliminaryActivityService);
    }

    @GetMapping("/characteristics")
    public String chooseCategory(Model model) {

        List<String> categories = this.categoryService.getAll()
                .stream().map(Category::getName).toList();
        model.addAttribute("categories",
                categories);

        return "agency/categoryCharacteristics";
    }

    @PostMapping("/characteristics")
    public String applyCategory(@RequestParam("categoryValue") String category,
                                HttpServletRequest request) {
        this.sessionContext.setCategoryValueToSession(request, category);
        return "redirect:/agency/route/places";
    }

    @PostMapping("/places/add/{id}")
    public String addPlaceToRoute(@PathVariable("id") Long id,
                                  HttpSession session) {
        this.preliminaryActivityService.save(session.getId(), id, false);
        return "redirect:/agency/route/places";
    }

    @PostMapping("/places/delete/{id}")
    public String deletePlaceFromRoute(@PathVariable("id") Long id,
                                       HttpSession session) {
        this.preliminaryActivityService.deleteById(session.getId(), id);
        return "redirect:/agency/route/places";
    }

    @GetMapping("/route/create")
    public String createRoute(Model model, HttpSession session) {
        CreatedRoute route = new RouteCreator().createNewRoute(
                this.preliminaryActivityService.getAll(session.getId()),
                null, "", "");

        route.setRoutePrice((Double) session.getAttribute(PRICE_ATTRIBUTE));

        this.routeService.saveAgencyRoute(
                (String) session.getAttribute(NAME_ATTRIBUTE),
                (String) session.getAttribute(DESCRIPTION_ATTRIBUTE),
                this.sessionContext.getUserLogin(),
                this.sessionContext.getCurrentCity(session),
                new byte[] {0}, route,
                (Integer) session.getAttribute(TOURISTS_ATTRIBUTE),
                (String) session.getAttribute(START_DATE_ATTRIBUTE),
                (String) session.getAttribute(END_DATE_ATTRIBUTE));

        this.addCreatedRouteDetailsToPage(model, session);
        this.preliminaryActivityService.deleteAll(session.getId());

        return "agency/detailedRoutePage";
    }

    private void addCreatedRouteDetailsToPage(Model model, HttpSession session) {
        Route route = this.routeService.getByOwnerAndName(
                this.sessionContext.getUserLogin(), (String) session.getAttribute(NAME_ATTRIBUTE));
        model.addAttribute("name", route.getName());
        model.addAttribute("city", route.getCity().getName());
        model.addAttribute("price", route.getPrice());
        model.addAttribute("time", route.getTime());
        model.addAttribute("length", route.getLength());
        model.addAttribute("description", route.getDescription());
        model.addAttribute("activities",
                this.routeService.getRouteActivities(route.getId()));
    }
}
