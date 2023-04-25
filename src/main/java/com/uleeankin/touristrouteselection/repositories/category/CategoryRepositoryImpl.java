package com.uleeankin.touristrouteselection.repositories.category;

import com.uleeankin.touristrouteselection.models.activity.Category;
import com.uleeankin.touristrouteselection.utils.config.CategoryConfig;
import com.uleeankin.touristrouteselection.utils.mappers.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryConfig categoryConfigurator;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepositoryImpl(CategoryConfig categoryConfigurator, JdbcTemplate jdbcTemplate) {
        this.categoryConfigurator = categoryConfigurator;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        return this.jdbcTemplate.query(this.categoryConfigurator.getAll(), new CategoryRowMapper());
    }

    @Override
    public Optional<Category> findByName(String name) {
        try {
            Category category = this.jdbcTemplate.queryForObject(
                    this.categoryConfigurator.getOne(), new CategoryRowMapper(), name);
            return Optional.ofNullable(category);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(String name) {
        this.jdbcTemplate.update(this.categoryConfigurator.getSave(), name);
    }
}
