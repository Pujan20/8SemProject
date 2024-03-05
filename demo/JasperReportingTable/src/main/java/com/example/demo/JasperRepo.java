package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JasperRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JasperRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Person> findAll() {
        String sql = "SELECT id, firstName, lastName, age, location FROM person";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }
}
