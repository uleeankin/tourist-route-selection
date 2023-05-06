package com.uleeankin.touristrouteselection.route.feedback.repository;

import com.uleeankin.touristrouteselection.route.feedback.model.RouteFeedback;

import java.sql.Date;
import java.util.List;

public interface RouteFeedbackRepository {

    List<RouteFeedback> findAllById(Long routeId);

    void addFeedback(String userLogin, Long routeId,
                     Integer assessment, String feedback,
                     Date creationDate);

    Integer countFeedbackNumber(Long routeId);

    Double calculateAverageAssessment(Long routeId);

}
