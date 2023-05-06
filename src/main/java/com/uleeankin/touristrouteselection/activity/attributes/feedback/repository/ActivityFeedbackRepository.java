package com.uleeankin.touristrouteselection.activity.attributes.feedback.repository;

import com.uleeankin.touristrouteselection.activity.attributes.feedback.model.ActivityFeedback;

import java.sql.Date;
import java.util.List;

public interface ActivityFeedbackRepository {

    List<ActivityFeedback> findAllById(Long activityId);

    void addFeedback(String userLogin, Long activityId,
                     Integer assessment, String feedback,
                     Date creationDate);

    Integer countFeedbackNumber(Long activityId);

    Double calculateAverageAssessment(Long activityId);

}
