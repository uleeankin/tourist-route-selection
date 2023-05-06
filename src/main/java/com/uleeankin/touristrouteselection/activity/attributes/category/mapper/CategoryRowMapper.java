package com.uleeankin.touristrouteselection.activity.attributes.category.mapper;

import com.uleeankin.touristrouteselection.activity.attributes.category.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(rs.getLong("category_id"),
                rs.getString("category_name"));
    }
}
