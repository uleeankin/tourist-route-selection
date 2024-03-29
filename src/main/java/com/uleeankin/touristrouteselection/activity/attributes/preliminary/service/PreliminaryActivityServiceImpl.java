package com.uleeankin.touristrouteselection.activity.attributes.preliminary.service;

import com.uleeankin.touristrouteselection.activity.attributes.preliminary.model.PreliminaryActivity;
import com.uleeankin.touristrouteselection.activity.attributes.preliminary.repository.PreliminaryActivityRepository;
import com.uleeankin.touristrouteselection.utils.DateTimeService;
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
                DateTimeService.convert(time));
    }

    @Override
    public boolean hasEvents(String id) {
        return this.repository.hasEvents(id, true);
    }

    @Override
    public List<PreliminaryActivity> getAll(String id) {
        return this.repository.findAll(id);
    }

    @Override
    public List<PreliminaryActivity> getAllPreliminary(String id) {
        return this.repository.findAllPreliminary(id);
    }
}
