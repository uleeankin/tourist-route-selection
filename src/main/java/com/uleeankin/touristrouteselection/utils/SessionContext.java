package com.uleeankin.touristrouteselection.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class SessionContext {

    public static void addUserNameToPage(Model model) {
        model.addAttribute("username", "@" +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public static String getUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
