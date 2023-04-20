package com.uleeankin.touristrouteselection.services.feedback;

import com.uleeankin.touristrouteselection.models.route.RouteFeedback;

import java.util.List;

public interface RouteFeedbackService {

    List<RouteFeedback> getAll(Long routeId);

    void addFeedback(String login, Long routeId, Integer assessment,
                     String feedback);

    Integer getFeedbackNumber(Long routeId);

    Double getAverageAssessment(Long routeId);
}
