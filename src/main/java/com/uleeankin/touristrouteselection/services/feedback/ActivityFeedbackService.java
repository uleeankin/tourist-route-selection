package com.uleeankin.touristrouteselection.services.feedback;

import com.uleeankin.touristrouteselection.models.activity.ActivityFeedback;

import java.util.List;

public interface ActivityFeedbackService {

    List<ActivityFeedback> getAll(Long activityId);

    void addFeedback(String login, Long activityId, Integer assessment,
                     String feedback);

    Integer getFeedbackNumber(Long activityId);

    Double getAverageAssessment(Long activityId);

}
