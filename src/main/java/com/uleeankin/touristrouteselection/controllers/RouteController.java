package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventInfo;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventSession;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventStatus;
import com.uleeankin.touristrouteselection.activity.attributes.event.service.EventService;
import com.uleeankin.touristrouteselection.algorithm.RouteCreator;
import com.uleeankin.touristrouteselection.city.model.City;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.route.model.AgencyRoute;
import com.uleeankin.touristrouteselection.route.model.CompletedRoute;
import com.uleeankin.touristrouteselection.route.model.CreatedRoute;
import com.uleeankin.touristrouteselection.route.model.Route;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import com.uleeankin.touristrouteselection.activity.model.ActivityStatus;
import com.uleeankin.touristrouteselection.route.feedback.model.RouteFeedback;
import com.uleeankin.touristrouteselection.activity.service.ActivityService;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.service.PreliminaryActivityService;
import com.uleeankin.touristrouteselection.activity.attributes.category.service.CategoryService;
import com.uleeankin.touristrouteselection.city.service.CityService;
import com.uleeankin.touristrouteselection.route.feedback.service.RouteFeedbackService;
import com.uleeankin.touristrouteselection.route.service.AgencyRouteService;
import com.uleeankin.touristrouteselection.route.service.RouteService;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import com.uleeankin.touristrouteselection.utils.DateTimeService;
import com.uleeankin.touristrouteselection.utils.json.JSONConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    private final EventService eventService;

    private final RouteService routeService;

    private final AgencyRouteService agencyRouteService;

    private final RouteFeedbackService routeFeedbackService;

    private final PreliminaryActivityService preliminaryActivityService;

    private final SessionContext sessionContext;

    @Autowired
    public RouteController(CityService cityService,
                           CategoryService categoryService,
                           ActivityService activityService,
                           EventService eventService,
                           RouteService routeService,
                           AgencyRouteService agencyRouteService,
                           RouteFeedbackService routeFeedbackService,
                           PreliminaryActivityService preliminaryActivityService,
                           SessionContext sessionContext) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.eventService = eventService;
        this.routeService = routeService;
        this.agencyRouteService = agencyRouteService;
        this.routeFeedbackService = routeFeedbackService;
        this.preliminaryActivityService = preliminaryActivityService;
        this.sessionContext = sessionContext;
    }

    @GetMapping
    public String getStartCreatingRoutePage(Model model) {
        sessionContext.addUserNameToPage(model);
        model.addAttribute("cities",
                this.cityService.getAll().stream()
                        .map(City::getName).toList());
        return "route/routeCharacteristics";
    }

    @PostMapping
    public String addRouteCharacteristics(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("cityname") String city,
                                          @RequestParam("date") String date,
                                          HttpServletRequest request) {

        sessionContext.setRouteNameValueToSession(request, name);
        sessionContext.setRouteDescriptionValueToSession(request, description);
        sessionContext.setCityValueToSession(request, city);
        sessionContext.setSessionDateAttribute(request, date);

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
    public String applyCategory(@RequestParam("categoryValue") String category,
                                HttpServletRequest request) {
        sessionContext.setCategoryValueToSession(request, category);
        return "redirect:/route/places";
    }

    @GetMapping("/places")
    public String getPlacesPage(Model model, HttpSession session) {
        this.sessionContext.addUserNameToPage(model);
        model.addAttribute("category",
                this.sessionContext.getCurrentCategory(session));
        model.addAttribute("tourDate",
                this.sessionContext.getSessionDateAttribute(session));

        List<Activity> activities = this.activityService
                .getFavouritesByCityAndCategory(
                    this.sessionContext.getUserLogin(),
                    this.sessionContext.getCurrentCity(session),
                    this.sessionContext.getCurrentCategory(session));
        model.addAttribute("activities",
                getActivities(activities, session));

        if (!this.sessionContext.getSessionDateAttribute(session).isEmpty()) {
            List<Event> favouriteEvents = this.eventService
                    .getFavouritesByCriteria(
                            this.sessionContext.getUserLogin(),
                            this.sessionContext.getCurrentCity(session),
                            this.sessionContext.getCurrentCategory(session),
                            this.sessionContext.getSessionDateAttribute(session));

            List<EventInfo> events = new ArrayList<>();

            for (Event favouriteEvent : favouriteEvents) {
                events.add(new EventInfo(favouriteEvent,
                        this.eventService.getSchedule(
                                favouriteEvent.getActivity().getId())
                                .stream().map(EventSession::getSessionTime).toList()));
            }

            model.addAttribute("events",
                    getEvents(events, session));
        }

        return "route/placesForRoutePage";
    }

    private List<ActivityStatus> getActivities(List<Activity> activities,
                                               HttpSession session) {

        return this.activityService.getActivityStatuses(
                activities, session, this.preliminaryActivityService);
    }

    private List<EventStatus> getEvents(List<EventInfo> events,
                                            HttpSession session) {

        List<EventStatus> eventStatuses = new ArrayList<>();
        List<PreliminaryActivity> addedEvents =
                this.preliminaryActivityService.getAll(session.getId());

        for (EventInfo event : events) {
            eventStatuses.add(new EventStatus(event,
                    !addedEvents.stream()
                            .map(addedEvent -> addedEvent.getActivity().getId())
                            .toList()
                            .contains(event.getEvent().getActivity().getId())));
        }

        return eventStatuses;
    }

    @PostMapping("/places/add/{id}")
    public String addPlaceToRoute(@PathVariable("id") Long id,
                                  HttpSession session) {
        this.preliminaryActivityService.save(session.getId(), id, false);
        return "redirect:/route/places";
    }

    @PostMapping("/events/add/{id}")
    public String addEventToRoute(@PathVariable("id") Long id,
                                  @RequestParam("eventTime") String eventTime,
                                  HttpSession session) {
        this.preliminaryActivityService.save(session.getId(), id, true);
        this.preliminaryActivityService.updateTime(session.getId(), id, eventTime);
        return "redirect:/route/places";
    }

    @PostMapping("/places/delete/{id}")
    public String deletePlaceFromRoute(@PathVariable("id") Long id,
                                       HttpSession session) {
        this.preliminaryActivityService.deleteById(session.getId(), id);
        return "redirect:/route/places";
    }

    @GetMapping("/constraints")
    public String getConstraintsAddingPage(Model model, HttpSession session) {
        this.sessionContext.addUserNameToPage(model);

        model.addAttribute("hasEvents",
                this.preliminaryActivityService.hasEvents(session.getId()));

        model.addAttribute("setTimeConstraint",
                session.getAttribute("timeConstraint"));

        model.addAttribute("setPriceConstraint",
                session.getAttribute("priceConstraint"));

        model.addAttribute("setStartTime",
                session.getAttribute("startTime"));

        return "route/routeConstraintsPage";
    }

    @PostMapping("/constraint/add/time")
    public String addTimeConstraint(
            @RequestParam("maxTime") String timeConstraint,
            HttpSession session) {

        session.setAttribute("timeConstraint", timeConstraint);
        return "redirect:/route/constraints";
    }

    @PostMapping("/constraint/add/price")
    public String addPriceConstraint(
            @RequestParam("maxPrice") Double priceConstraint,
            HttpSession session) {
        session.setAttribute("priceConstraint", priceConstraint);
        return "redirect:/route/constraints";
    }

    @PostMapping("/constraint/add/start")
    public String addRouteStartTime(
            @RequestParam("startTime") String startTime,
            HttpSession session) {

        session.setAttribute("startTime", startTime);
        return "redirect:/route/constraints";
    }

    @PostMapping("/create")
    public String createRoute(HttpSession session) {
        String timeConstraint = session.getAttribute("timeConstraint") != null ?
                (String) session.getAttribute("timeConstraint") : "";

        Double priceConstraint = session.getAttribute("priceConstraint") != null ?
                (Double) session.getAttribute("priceConstraint") : null;

        String startTime = session.getAttribute("startTime") != null ?
                (String) session.getAttribute("startTime") : "";

        this.createRoute(session, timeConstraint,
                priceConstraint, startTime);
        return "redirect:/route/create";
    }

    private void createRoute(HttpSession session, String timeConstraint,
                             Double priceConstraint, String routeStartTime) {

        CreatedRoute route = new RouteCreator().createNewRoute(
                        this.preliminaryActivityService.getAll(session.getId()),
                        priceConstraint, timeConstraint, routeStartTime);

        this.routeService.save(
                this.sessionContext.getRouteNameAttribute(session),
                this.sessionContext.getRouteDescriptionAttribute(session),
                this.sessionContext.getUserLogin(),
                this.sessionContext.getCurrentCity(session), route);

        this.preliminaryActivityService.deleteAll(session.getId());
        session.removeAttribute("timeConstraint");
        session.removeAttribute("priceConstraint");
        session.removeAttribute("startTime");
    }

    @GetMapping("/create")
    public String showCreatedRoute(Model model, HttpSession session) {
        sessionContext.addUserNameToPage(model);
        Route route = this.routeService.getByOwnerAndName(
                this.sessionContext.getUserLogin(),
                this.sessionContext.getRouteNameAttribute(session));
        model.addAttribute("name", route.getName());
        this.addRouteDetailsToModel(model, route);

        List<Activity> activities = this.routeService.getRouteActivities(route.getId());
        model.addAttribute("activities", activities);

        model.addAttribute("locationJSON",
                new JSONConverter().getCoordinatesJSON(activities));

        return "route/detailedRoutePage";
    }

    @GetMapping("/owner/{id}")
    public String getOwnerRouteDetails(@PathVariable("id") Long routeId,  Model model) {
        sessionContext.addUserNameToPage(model);
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
        model.addAttribute("locationJSON",
                new JSONConverter().getCoordinatesJSON(activities));

        this.addFeedback(model, routeId);

        return "tourist/ownerRouteDetails";
    }

    @GetMapping("/{id}")
    public String getRouteDetails(@PathVariable("id") Long routeId,  Model model) {
        sessionContext.addUserNameToPage(model);
        Route route = this.routeService.getById(routeId);

        model.addAttribute("owner", route.getOwner());
        model.addAttribute("routeName", route.getName());
        model.addAttribute("routeId", route.getId());
        this.addRouteDetailsToModel(model, route);

        List<Activity> activities = this.routeService.getRouteActivities(route.getId());
        model.addAttribute("activities", activities);
        model.addAttribute("locationJSON",
                new JSONConverter().getCoordinatesJSON(activities));

        this.addFeedback(model, routeId);

        model.addAttribute("favourite",
                this.routeService.isFavourite(sessionContext.getUserLogin(), routeId));

        model.addAttribute("completed",
                this.routeService.isCompleted(sessionContext.getUserLogin(), routeId));

        model.addAttribute("isAgencyRoute",
                this.routeService.isAgencyRoute(routeId));

        model.addAttribute("booked",
                this.agencyRouteService.isBookedRoute(routeId));

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

        this.routeFeedbackService.addFeedback(sessionContext.getUserLogin(),
                routeId, assessment, comment);

        return "redirect:/route/{id}";
    }

    @PostMapping("/favourites/add/{id}")
    public String addToFavourites(@PathVariable("id") Long id) {

        this.routeService.addToFavourites(sessionContext.getUserLogin(),
                id);

        return "redirect:/route/{id}";
    }

    @GetMapping("/favourites")
    public String getFavouritesPage(Model model) {

        sessionContext.addUserNameToPage(model);

        List<Route> routes =
                this.routeService.getFavourites(sessionContext.getUserLogin());

        model.addAttribute("routes", routes);

        return "tourist/favouriteRoutes";
    }

    @GetMapping("/completed")
    public String getCompletedRoutesPage(Model model) {

        sessionContext.addUserNameToPage(model);

        List<CompletedRoute> routes =
                this.routeService.getCompleted(sessionContext.getUserLogin());

        model.addAttribute("routes", routes);

        return "tourist/completedRoutes";
    }

    @PostMapping("/complete/{id}")
    public String completeRoute(@PathVariable("id") Long routeId) {

        this.routeService.completeRoute(sessionContext.getUserLogin(), routeId);

        return "redirect:/route/{id}";
    }

    @PostMapping("/favourites/delete/{id}")
    public String deleteFromFavourites(@PathVariable("id") Long id) {

        this.routeService.deleteFromFavourites(sessionContext.getUserLogin(),
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

    @GetMapping("/book/{id}")
    public String showBookingPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("routeId", id);
        AgencyRoute route = this.agencyRouteService.getById(id);
        model.addAttribute("dates",
                DateTimeService.getDates(route.getStartDate(), route.getEndDate()));
        return "route/bookingPage";
    }



    @PostMapping("/book/{id}")
    public String bookRoute(@PathVariable("id") Long id,
                            @RequestParam("bookingDate") String bookingDate,
                            @RequestParam("tourists") Integer touristNumber) {
        AgencyRoute route = this.agencyRouteService.getById(id);
        if (this.agencyRouteService.getFreePlaceNumber(id, bookingDate) + touristNumber
                <= route.getMaxTouristNumber()) {
            this.agencyRouteService.bookRoute(
                    id, this.sessionContext.getUserLogin(),
                    bookingDate, touristNumber);
        }
        return "redirect:/route/{id}";
    }

    @GetMapping("/booked")
    public String getBookedRoutes(Model model) {
        this.sessionContext.addUserNameToPage(model);
        model.addAttribute("routes",
                this.agencyRouteService.getAllBooked(this.sessionContext.getUserLogin()));
        return "tourist/bookedRoutes";
    }
}
