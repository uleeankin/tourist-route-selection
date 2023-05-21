package com.uleeankin.touristrouteselection.controllers;

import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.service.ActivityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final ActivityService activityService;

    @Autowired
    public ImageController(ActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping("/display/{id}")
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Activity activity = this.activityService.getById(id);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(activity.getPhoto());
        response.getOutputStream().close();
    }
}
