package com.uleeankin.touristrouteselection.activity.attributes.event.repository;

import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventSession;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface EventRepository {

    void save(Long id, Date startDate, Date endDate, String owner);

    void saveSession(Long id, Time sessionTime);

    List<Event> findAll();

    List<Event> findByDate(Date routeDate);

    Event findById(Long id);

    void deleteById(Long id);

    void deleteSessions(Long id);

    void update(Long id, Date startDate, Date endDate);

    List<EventSession> findAllSessions(Long id);

    void deleteSession(Long id, Time time);

    void addSession(Long id, Time time);

    List<Event> findFavourites(String userId);

    List<Event> findFavouritesByCriteria(
            String userId, String city, String category, Date visitingDate);
}
