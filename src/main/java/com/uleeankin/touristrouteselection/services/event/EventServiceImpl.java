package com.uleeankin.touristrouteselection.services.event;

import com.uleeankin.touristrouteselection.repositories.event.EventRepository;
import com.uleeankin.touristrouteselection.utils.StringToTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void save(Long id, String startDate, String endDate, String owner,
                     String breakTime, String startTime, String endTime, String duration) {

        this.eventRepository.save(id, Date.valueOf(startDate), Date.valueOf(endDate), owner);

        List<Time> sessions = StringToTimeConverter.getSessions(
                startTime, endTime, breakTime, duration);

        for (Time session : sessions) {
            this.eventRepository.saveSession(id, session);
        }
    }
}
