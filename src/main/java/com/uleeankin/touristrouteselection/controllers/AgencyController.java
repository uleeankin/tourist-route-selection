package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.utils.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agency")
public class AgencyController {

    private final SessionContext sessionContext;

    @Autowired
    public AgencyController(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @GetMapping
    public String getMainPage(Model model) {
        this.sessionContext.addUserNameToPage(model);
        return "agency/main";
    }

    @GetMapping("/all")
    public String getAllRoutes(Model model) {
        this.sessionContext.addUserNameToPage(model);
        return "agency/allRoutes";
    }

    @GetMapping("/add")
    public String addNewRoute(Model model) {
        this.sessionContext.addUserNameToPage(model);
        return "agency/addRoute";
    }
}
