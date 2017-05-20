package io.codebonobos.daos;

import io.codebonobos.entities.Akcija;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by afilakovic on 20.05.17..
 */
@Component
public class AkcijaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AkcijaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAction(Akcija action) {
        String query = "INSERT INTO AKCIJA(VODITELJ_BROJ, LOKACIJA_LAT, LOKACIJA_LNG, RADIUS, OPIS, AKTIVNA) VALUES('"
            + action.getVoditelj() + "'," +
            " '" + action.getLocation().getLat() + "'," +
            " '" + action.getLocation().getLng() + "'," +
            " '" + action.getRadius() + "'," +
            " '" + action.getOpis() + "'," +
            " '" + action.isActive() + "')";

        jdbcTemplate.update(query);
    }
}
