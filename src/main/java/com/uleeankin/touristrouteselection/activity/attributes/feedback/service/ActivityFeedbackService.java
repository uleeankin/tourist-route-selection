package com.uleeankin.touristrouteselection.activity.attributes.feedback.service;

import com.uleeankin.touristrouteselection.activity.attributes.feedback.model.ActivityFeedback;

import java.util.List;

public interface ActivityFeedbackService {

    List<ActivityFeedback> getAll(Long activityId);

    void addFeedback(String login, Long activityId, Integer assessment,
                     String feedback);

    Integer getFeedbackNumber(Long activityId);

    Double getAverageAssessment(Long activityId);

}
