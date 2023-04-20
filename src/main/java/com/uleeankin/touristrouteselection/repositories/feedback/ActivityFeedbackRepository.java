package com.uleeankin.touristrouteselection.repositories.feedback;

import com.uleeankin.touristrouteselection.models.activity.ActivityFeedback;

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
