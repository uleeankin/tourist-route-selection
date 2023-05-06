package com.uleeankin.touristrouteselection.route.feedback.service;

import com.uleeankin.touristrouteselection.route.feedback.model.RouteFeedback;
import com.uleeankin.touristrouteselection.route.feedback.repository.RouteFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RouteFeedbackServiceImpl implements RouteFeedbackService {

    private final RouteFeedbackRepository feedbackRepository;

    @Autowired
    public RouteFeedbackServiceImpl(
            RouteFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<RouteFeedback> getAll(Long routeId) {
        return this.feedbackRepository.findAllById(routeId);
    }

    @Override
    public void addFeedback(String login, Long routeId, Integer assessment, String feedback) {
        this.feedbackRepository.addFeedback(login, routeId, assessment,
                feedback, new Date(new java.util.Date().getTime()));
    }

    @Override
    public Integer getFeedbackNumber(Long routeId) {
        return this.feedbackRepository.countFeedbackNumber(routeId);
    }

    @Override
    public Double getAverageAssessment(Long routeId) {
        return Math.ceil(this.feedbackRepository
                .calculateAverageAssessment(routeId) * 10) / 10.0;
    }
}
