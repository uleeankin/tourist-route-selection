package com.uleeankin.touristrouteselection.activity.attributes.event.service;

import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventSession;
import com.uleeankin.touristrouteselection.activity.attributes.event.repository.EventRepository;
import com.uleeankin.touristrouteselection.activity.model.Activity;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.repository.ActivityRepository;
import com.uleeankin.touristrouteselection.activity.attributes.coordinates.repository.CoordinateRepository;
import com.uleeankin.touristrouteselection.utils.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ActivityRepository activityRepository;
    private final CoordinateRepository coordinateRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            ActivityRepository activityRepository,
                            CoordinateRepository coordinateRepository) {
        this.eventRepository = eventRepository;
        this.activityRepository = activityRepository;
        this.coordinateRepository = coordinateRepository;
    }

    @Override
    public void save(Long id, String startDate, String endDate, String owner,
                     String breakTime, String startTime, String endTime, String duration) {

        this.eventRepository.save(id, Date.valueOf(startDate), Date.valueOf(endDate), owner);

        List<Time> sessions = TimeService.getSessions(
                startTime, endTime, breakTime, duration);

        for (Time session : sessions) {
            this.eventRepository.saveSession(id, session);
        }
    }

    @Override
    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public List<Event> getByDate(String date) {
        return this.eventRepository.findByDate(Date.valueOf(date));
    }

    @Override
    public Event getById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.eventRepository.deleteSessions(id);
        this.eventRepository.deleteById(id);
        Optional<Activity> activity = this.activityRepository.findById(id);
        this.activityRepository.deleteById(id);
        this.coordinateRepository.deleteById(activity.get().getCoordinate().getId());
    }

    @Override
    public void update(Long id, String name, String description, String time,
                       Double price, String startDate, String endDate, byte[] bytes) {
        this.activityRepository.update(id, name, description, TimeService.convert(time), price, bytes);
        this.eventRepository.update(id, Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Override
    public List<EventSession> getSchedule(Long id) {
        return this.eventRepository.findAllSessions(id);
    }

    @Override
    public void deleteSession(Long id, String time) {
        this.eventRepository.deleteSession(id, TimeService.convert(time));
    }

    @Override
    public void addSession(Long id, String time) {
        this.eventRepository.addSession(id,
                TimeService.convert(time));
    }

    @Override
    public List<Event> getFavourites(String userId) {
        return this.eventRepository.findFavourites(userId);
    }

    @Override
    public List<Event> getFavouritesByCriteria(
            String userId, String city, String category, String date) {

        return this.eventRepository.findFavouritesByCriteria(
                userId, city, category, Date.valueOf(date));
    }
}
