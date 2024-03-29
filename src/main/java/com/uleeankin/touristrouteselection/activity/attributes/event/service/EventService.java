package com.uleeankin.touristrouteselection.activity.attributes.event.service;

import com.uleeankin.touristrouteselection.activity.attributes.event.model.Event;
import com.uleeankin.touristrouteselection.activity.attributes.event.model.EventSession;

import java.util.List;

public interface EventService {

    void save(Long id, String startDate, String endDate, String owner,
              String breakTime, String startTime, String endTime, String duration);

    List<Event> getAll();

    List<Event> getByDate(String date);

    Event getById(Long id);

    void delete(Long id);

    void update(Long id, String name, String description, String time, Double price,
                String startDate, String endDate, byte[] bytes);

    List<EventSession> getSchedule(Long id);

    void deleteSession(Long id, String time);

    void addSession(Long id, String time);

    List<Event> getFavourites(String userId);

    List<Event> getFavouritesByCriteria(
            String userId, String city, String category, String date);

}
