package com.uleeankin.touristrouteselection.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class SessionContext {


    private static final String ROUTE_NAME_ATTRIBUTE = "routeName";
    private static final String ROUTE_DESCRIPTION_ATTRIBUTE = "routeDescription";
    private static final String SESSION_CITY_ATTRIBUTE = "currentCity";
    private static final String SESSION_CATEGORY_ATTRIBUTE = "currentCategory";
    private  static final String SESSION_DATE_ATTRIBUTE = "currentChosenDate";

    public void setSessionDateAttribute(HttpServletRequest request, String date) {
        request.getSession().setAttribute(SESSION_DATE_ATTRIBUTE, date);
    }

    public String getSessionDateAttribute(HttpSession session) {
        Optional<Object> currentDate =
                Optional.ofNullable(session.getAttribute(SESSION_DATE_ATTRIBUTE));
        return currentDate.isEmpty() ? "" : currentDate.get().toString();
    }

    public void setRouteNameValueToSession(HttpServletRequest request, String name) {
        request.getSession().setAttribute(ROUTE_NAME_ATTRIBUTE, name);
    }

    public String getRouteNameAttribute(HttpSession session) {
        return session.getAttribute(ROUTE_NAME_ATTRIBUTE).toString();
    }

    public void setRouteDescriptionValueToSession(HttpServletRequest request, String description) {
        request.getSession().setAttribute(ROUTE_DESCRIPTION_ATTRIBUTE, description);
    }

    public String getRouteDescriptionAttribute(HttpSession session) {
        return session.getAttribute(ROUTE_DESCRIPTION_ATTRIBUTE).toString();
    }

    public void setCityValueToSession(HttpServletRequest request, String cityName) {
        request.getSession().setAttribute(SESSION_CITY_ATTRIBUTE, cityName);
    }

    public void setCategoryValueToSession(HttpServletRequest request, String categoryName) {
        request.getSession().setAttribute(SESSION_CATEGORY_ATTRIBUTE, categoryName);
    }

    public String getCurrentCity(HttpSession session) {
        Optional<Object> currentCity =
                Optional.ofNullable(session.getAttribute(SESSION_CITY_ATTRIBUTE));
        return currentCity.isEmpty() ? "" : currentCity.get().toString();
    }

    public String getCurrentCategory(HttpSession session) {
        Optional<Object> currentCategory =
                Optional.ofNullable(session.getAttribute(SESSION_CATEGORY_ATTRIBUTE));
        return currentCategory.isEmpty() ? "" : currentCategory.get().toString();
    }

    public void addUserNameToPage(Model model) {
        model.addAttribute("username", "@" +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public String getUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
