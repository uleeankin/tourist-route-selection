package com.uleeankin.touristrouteselection.services.event;

import com.uleeankin.touristrouteselection.models.activity.Event;
import com.uleeankin.touristrouteselection.models.activity.EventSession;

import java.util.List;

public interface EventService {

    void save(Long id, String startDate, String endDate, String owner,
              String breakTime, String startTime, String endTime, String duration);

    List<Event> getAll();

    List<Event> getByDate(String date);

    Event getById(Long id);

    void delete(Long id);

    void update(Long id, String name, String description, String time, Double price,
                String startDate, String endDate);

    List<EventSession> getSchedule(Long id);

    void deleteSession(Long id, String time);

    void addSession(Long id, String time);

    List<Event> getFavourites(String userId);

}
