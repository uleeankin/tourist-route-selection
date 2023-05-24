package com.uleeankin.touristrouteselection.route.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingInfo {
    private String login;
    private String surname;
    private String name;
    private String lastname;
    private Integer bookingNumber;

    @Override
    public String toString() {
        return "@" + login + " ("
                + surname + " "
                + name + " "
                + lastname + "): "
                + bookingNumber + '\n';
    }
}
