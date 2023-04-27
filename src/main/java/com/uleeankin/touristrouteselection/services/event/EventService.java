package com.uleeankin.touristrouteselection.services.event;

import org.springframework.web.bind.annotation.RequestParam;

public interface EventService {

    void save(Long id, String startDate, String endDate, String owner,
              String breakTime, String startTime, String endTime, String duration);

}
