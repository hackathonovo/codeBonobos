package io.codebonobos.daos;

import io.codebonobos.entities.Akcija;
import io.codebonobos.entities.HgssLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public int saveAction(Akcija action) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO AKCIJA(VODITELJ_BROJ, LOKACIJA_LAT, LOKACIJA_LNG, RADIUS, OPIS, AKTIVNA) VALUES(?,?,?,?,?,?)";


        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, action.getVoditelj());
            ps.setString(2, action.getLocation() == null ? null : action.getLocation().getLat());
            ps.setString(3, action.getLocation() == null ? null : action.getLocation().getLng());
            ps.setDouble(4, action.getRadius());
            ps.setString(5, action.getOpis());
            ps.setBoolean(6, action.isActive());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Akcija> getAll() {
        String query = "SELECT * FROM AKCIJA";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        List<Akcija> retval = new ArrayList<>();

        for (Map<String, Object> dbRow : result) {
            retval.add(mapToAction(dbRow));
        }

        return retval;
    }

    public List<Akcija> getActive() {
        String query = "SELECT * FROM AKCIJA WHERE AKTIVNA = TRUE";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        List<Akcija> retval = new ArrayList<>();

        for (Map<String, Object> dbRow : result) {
            retval.add(mapToAction(dbRow));
        }

        return retval;
    }

    public void finishAction(String actionId) {
        String query = "UPDATE AKCIJA SET AKTIVNA=FALSE WHERE ID = '" + actionId + "'";
        jdbcTemplate.update(query);
    }

    private Akcija mapToAction(Map<String, Object> dbRow) {
        Akcija action = new Akcija();
        HgssLocation location = new HgssLocation();
        if (dbRow.get("ID") != null) {
            action.setId((int) dbRow.get("ID"));
        }
        if (dbRow.get("VODITELJ_BROJ") != null) {
            action.setVoditelj((String) dbRow.get("VODITELJ_BROJ"));
        }
        if (dbRow.get("LOKACIJA_LAT") != null) {
            location.setLat((String) dbRow.get("LOKACIJA_LAT"));
        }
        if (dbRow.get("LOKACIJA_LNG") != null) {
            location.setLng((String) dbRow.get("LOKACIJA_LNG"));
        }
        if (dbRow.get("RADIUS") != null) {
            action.setRadius((double) dbRow.get("RADIUS"));
        }
        if (dbRow.get("OPIS") != null) {
            action.setOpis((String) dbRow.get("OPIS"));
        }
        if (dbRow.get("AKTIVNA") != null) {
            action.setIsActive((boolean) dbRow.get("AKTIVNA"));
        }

        action.setLocation(location);

        return action;
    }
}
