package com.uleeankin.touristrouteselection.repositories.feedback;

import com.uleeankin.touristrouteselection.models.route.RouteFeedback;

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
