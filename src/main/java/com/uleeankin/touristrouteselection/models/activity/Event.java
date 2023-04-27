package com.uleeankin.touristrouteselection.models.activity;

import com.uleeankin.touristrouteselection.models.user.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Activity activity;

    private Date startDate;

    private Date endDate;

    private Organization organization;

}
