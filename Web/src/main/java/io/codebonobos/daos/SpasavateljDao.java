package io.codebonobos.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by afilakovic on 20.05.17..
 */
public class SpasavateljDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SpasavateljDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    


}
