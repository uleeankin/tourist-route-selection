package com.uleeankin.touristrouteselection.activity.attributes.preliminary.service;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.repository.PreliminaryActivityRepository;
import com.uleeankin.touristrouteselection.utils.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreliminaryActivityServiceImpl
        implements PreliminaryActivityService {

    private final PreliminaryActivityRepository repository;

    @Autowired
    public PreliminaryActivityServiceImpl(
            PreliminaryActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(String id, Long activityId, boolean eventStatus) {
        this.repository.save(id, activityId, eventStatus);
    }

    @Override
    public void updateCompulsoryStatus(String id, Long activityId, boolean newStatus) {
        this.repository.updateCompulsoryStatus(id, activityId, newStatus);
    }

    @Override
    public void updateEventStatus(String id, Long activityId, boolean newStatus) {
        this.repository.updateEventStatus(id, activityId, newStatus);
    }

    @Override
    public void deleteAll(String id) {
        this.repository.deleteAll(id);
    }

    @Override
    public void deleteById(String id, Long activityId) {
        this.repository.deleteById(id, activityId);
    }

    @Override
    public void updateTime(String id, Long activityId, String time) {
        this.repository.updateTime(id, activityId,
                TimeService.convert(time));
    }

    @Override
    public List<PreliminaryRouteActivity> getAll(String id) {
        return this.repository.findAll(id);
    }

    @Override
    public List<PreliminaryRouteActivity> getAllPreliminary(String id) {
        return this.repository.findAllPreliminary(id);
    }
}
