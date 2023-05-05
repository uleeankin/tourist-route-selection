package com.uleeankin.touristrouteselection.services.activity;

import com.uleeankin.touristrouteselection.models.activity.PreliminaryRouteActivity;
import com.uleeankin.touristrouteselection.repositories.activity.PreliminaryActivityRepository;
import com.uleeankin.touristrouteselection.utils.ToTimeConverter;
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
    public void save(String id, Long activityId) {
        this.repository.save(id, activityId);
    }

    @Override
    public void updateStatus(String id, Long activityId, boolean newStatus) {
        this.repository.updateStatus(id, activityId, newStatus);
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
                ToTimeConverter.convert(time));
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
