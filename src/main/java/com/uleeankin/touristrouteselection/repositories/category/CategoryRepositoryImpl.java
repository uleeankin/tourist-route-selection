package com.uleeankin.touristrouteselection.repositories.category;

import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.utils.mappers.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String ALL_CATEGORIES =
            "select category_id, category_name from categories;";

    private static final String CATEGORY_BY_NAME =
            "select category_id, category_name from categories where category_name = ?;";

    private static final String SAVE_CATEGORY =
            "insert into categories (category_name) values (?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        return this.jdbcTemplate.query(ALL_CATEGORIES, new CategoryRowMapper());
    }

    @Override
    public Optional<Category> findByName(String name) {
        try {
            Category category = this.jdbcTemplate.queryForObject(
                    CATEGORY_BY_NAME, new CategoryRowMapper(), name);
            return Optional.ofNullable(category);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(String name) {
        this.jdbcTemplate.update(SAVE_CATEGORY, name);
    }
}
