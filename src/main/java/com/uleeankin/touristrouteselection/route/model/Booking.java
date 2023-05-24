package com.uleeankin.touristrouteselection.route.model;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    private Date date;
    private String bookingInfo;
}
