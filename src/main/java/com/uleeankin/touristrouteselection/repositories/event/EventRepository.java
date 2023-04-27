package com.uleeankin.touristrouteselection.repositories.event;

import java.sql.Date;
import java.sql.Time;

public interface EventRepository {

    void save(Long id, Date startDate, Date endDate, String owner);

    void saveSession(Long id, Time sessionTime);

}
