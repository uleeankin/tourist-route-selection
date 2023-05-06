package com.uleeankin.touristrouteselection.activity.attributes.feedback.service;

import com.uleeankin.touristrouteselection.activity.attributes.feedback.model.ActivityFeedback;
import com.uleeankin.touristrouteselection.activity.attributes.feedback.repository.ActivityFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ActivityFeedbackServiceImpl implements ActivityFeedbackService {

    private final ActivityFeedbackRepository activityFeedbackRepository;

    @Autowired
    public ActivityFeedbackServiceImpl(
            ActivityFeedbackRepository activityFeedbackRepository) {
        this.activityFeedbackRepository = activityFeedbackRepository;
    }

    @Override
    public List<ActivityFeedback> getAll(Long activityId) {
        return this.activityFeedbackRepository.findAllById(activityId);
    }

    @Override
    public void addFeedback(String login, Long activityId, Integer assessment, String feedback) {

        this.activityFeedbackRepository.addFeedback(login, activityId, assessment,
                feedback, new Date(new java.util.Date().getTime()));

    }

    @Override
    public Integer getFeedbackNumber(Long activityId) {
        return this.activityFeedbackRepository.countFeedbackNumber(activityId);
    }

    @Override
    public Double getAverageAssessment(Long activityId) {
        return Math.ceil(this.activityFeedbackRepository
                        .calculateAverageAssessment(activityId) * 10) / 10.0;
    }
}
