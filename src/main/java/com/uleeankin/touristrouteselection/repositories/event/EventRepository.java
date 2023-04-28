package com.uleeankin.touristrouteselection.repositories.event;

import com.uleeankin.touristrouteselection.models.activity.Event;
import com.uleeankin.touristrouteselection.models.activity.EventSession;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface EventRepository {

    void save(Long id, Date startDate, Date endDate, String owner);

    void saveSession(Long id, Time sessionTime);

    List<Event> findAll();

    Event findById(Long id);

    void deleteById(Long id);

    void deleteSessions(Long id);

    void update(Long id, Date startDate, Date endDate);

    List<EventSession> findAllSessions(Long id);

    void deleteSession(Long id, Time time);

    void addSession(Long id, Time time);
}
