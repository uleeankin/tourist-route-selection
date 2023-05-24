package com.uleeankin.touristrouteselection.route.mapper;

import com.uleeankin.touristrouteselection.route.model.BookingInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingInfoRowMapper implements RowMapper<BookingInfo> {
    @Override
    public BookingInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BookingInfo(rs.getString("login"),
                rs.getString("tourist_surname"),
                rs.getString("tourist_name"),
                rs.getString("tourist_lastname"),
                rs.getInt("tourist_number"));
    }
}
