package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.user.model.Tourist;
import com.uleeankin.touristrouteselection.utils.SessionContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    private final SessionContext sessionContext;

    @Autowired
    public RecommendationController(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @GetMapping("/parameters")
    public String getParametersAddingPage(Model model, HttpSession session) {
        this.sessionContext.addUserNameToPage(model);

        List<String> restrictions = new ArrayList<>();
        restrictions.add("0+");
        restrictions.add("6+");
        restrictions.add("12+");
        restrictions.add("16+");
        restrictions.add("18+");

        List<String> traffic = new ArrayList<>();
        traffic.add("Низкий");
        traffic.add("Средний");
        traffic.add("Высокий");

        model.addAttribute("ageRestrictionList", restrictions);
        model.addAttribute("trafficList", traffic);

        return "recommendation/recommendationParametersPage";
    }
}
