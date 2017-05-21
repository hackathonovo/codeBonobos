package io.codebonobos.daos;

import io.codebonobos.entities.Akcija;
import io.codebonobos.entities.HgssLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
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
        String query = "INSERT INTO AKCIJA(VODITELJ_BROJ, LOK_LAT, LOK_LNG, RADIUS, OPIS, AKTIVNA, LOC_MEETING, TIME_MEETING, PRIO) VALUES(?,?,?,?,?,?,?,?,?)";


        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, action.getVoditelj());
            ps.setString(2, action.getLocation() == null ? null : action.getLocation().getLat());
            ps.setString(3, action.getLocation() == null ? null : action.getLocation().getLng());
            ps.setDouble(4, action.getRadius());
            ps.setString(5, action.getOpis());
            ps.setBoolean(6, action.isActive());
            ps.setString(7, action.getMeetingLocation());
            ps.setString(8, action.getMeetingTime());
            ps.setInt(9, action.getPriority());
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

    public Akcija getActionById(String id) throws Exception {
        String query = "SELECT * FROM AKCIJA WHERE ID_A LIKE '" + id + "'";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        if (result == null || result.isEmpty()) {
            throw new Exception("No such action");
        }

        return mapToAction(result.get(0));
    }

    public Akcija getActiveActionById(String userId) throws Exception {
        String query = "SELECT * FROM SPASAVATELJ_AKCIJA AS SA " +
            "JOIN AKCIJA AS A ON A.ID_A = SA.ID_AKCIJA " +
            "WHERE SA.ID_SPASAVATELJ = " + userId + " AND A.AKTIVNA = TRUE AND SA.PRIHVATIO = TRUE";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        if (result == null || result.isEmpty()) {
            throw new Exception("No such action");
        }

        return mapToAction(result.get(0));
    }

    public void finishAction(String actionId) {
        String query = "UPDATE AKCIJA SET AKTIVNA=FALSE WHERE ID_A = '" + actionId + "'";
        jdbcTemplate.update(query);
    }

    public List<String> addInvitedRescuers(String actionId, List<Integer> userIds) {
        List<String> devTokens = new ArrayList<>();
        userIds.forEach(id -> {
            String query = "INSERT INTO SPASAVATELJ_AKCIJA VALUES(" + id + ", " + actionId + ", FALSE)";
            jdbcTemplate.update(query);
            devTokens.add(jdbcTemplate.queryForObject("SELECT DEV_TOKEN FROM SPASAVATELJ WHERE ID = " + id, String.class));
        });

        return devTokens;
    }

    private Akcija mapToAction(Map<String, Object> dbRow) {
        Akcija action = new Akcija();
        HgssLocation location = new HgssLocation();

        if (dbRow.get("ID_A") != null) {
            action.setId((int) dbRow.get("ID_A"));
        }
        if (dbRow.get("VODITELJ_BROJ") != null) {
            action.setVoditelj((String) dbRow.get("VODITELJ_BROJ"));
        }
        if (dbRow.get("LOK_LAT") != null) {
            location.setLat((String) dbRow.get("LOK_LAT"));
        }
        if (dbRow.get("LOK_LNG") != null) {
            location.setLng((String) dbRow.get("LOK_LNG"));
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
        if (dbRow.get("LOC_MEETING") != null) {
            action.setMeetingLocation((String) dbRow.get("LOC_MEETING"));
        }
        if (dbRow.get("TIME_MEETING") != null) {
            action.setMeetingTime((String) dbRow.get("TIME_MEETING"));
        }
        if (dbRow.get("PRIO") != null) {
            action.setPriority((int) dbRow.get("PRIO"));
        }


        action.setLocation(location);

        return action;
    }


}
