package io.codebonobos.daos;

import io.codebonobos.entities.HgssLocation;
import io.codebonobos.entities.Spasavatelj;
import io.codebonobos.enums.HgssIskustvo;
import io.codebonobos.enums.HgssSpecijalnost;
import io.codebonobos.utils.RescuerListsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by afilakovic on 20.05.17..
 */
@Component
public class SpasavateljDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SpasavateljDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Spasavatelj getById(int id) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE ID = '" + id + "'");

        if (result == null || result.isEmpty()) {
            throw new Exception("No such user");
        }

        return mapToSpasavatelj(result.get(0));
    }

    public RescuerListsWrapper getRescuersByGroups() {
        List<Map<String, Object>> inAction = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = SA.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID_A GROUP BY S.ID HAVING A.AKTIVNA = TRUE AND SA.PRIHVATIO = TRUE");
        List<Map<String, Object>> inactive = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE AKTIVAN = FALSE");

        List<Spasavatelj> avail = getAvailable();

        List<Spasavatelj> action = new ArrayList<>();
        for (Map<String, Object> dbRow : inAction) {
            action.add(mapToSpasavatelj(dbRow));
        }

        List<Spasavatelj> notActive = new ArrayList<>();
        for (Map<String, Object> dbRow : inactive) {
            notActive.add(mapToSpasavatelj(dbRow));
        }

        return new RescuerListsWrapper(avail, action, notActive);
    }



    public List<Spasavatelj> getAvailable() {
        List<Map<String, Object>> available = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = SA.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID_A GROUP BY S.ID HAVING (A.AKTIVNA = FALSE OR A.AKTIVNA IS NULL) AND S.AKTIVAN = TRUE");

        List<Spasavatelj> avail = new ArrayList<>();
        for (Map<String, Object> dbRow : available) {
            avail.add(mapToSpasavatelj(dbRow));
        }

        return avail;
    }

    public Spasavatelj getByFbToken(String fbToken) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE FB_TOKEN = '" + fbToken + "'");

        if (result == null || result.isEmpty()) {
            throw new Exception("No such user");
        }

        return mapToSpasavatelj(result.get(0));
    }

    public int saveFromFbTokenAndGetId(String name, String fbToken) throws Exception {
        String query = "INSERT INTO SPASAVATELJ(IME, FB_TOKEN) VALUES('" + name + "', '" + fbToken + "')";
        jdbcTemplate.update(query);

        return (int) jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE FB_TOKEN = '" + fbToken + "'").get(0).get("ID");
    }

    public Spasavatelj getByLogin(String username, String password, String devToken) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE USERNAME = '" + username + "' AND PWORD = '" + password + "'");

        Spasavatelj spasavatelj = mapToSpasavatelj(result.get(0));

        if (getDeviceTokenById(spasavatelj.getId()) == null && devToken != null) {
            jdbcTemplate.execute("UPDATE SPASAVATELJ SET DEV_TOKEN = '" + devToken + "' WHERE ID = " + spasavatelj.getId());
        }
        if (result == null || result.isEmpty()) {
            throw new Exception("No such user");
        }

        return spasavatelj;
    }

    public void saveRescuer(Spasavatelj rescuer) {
        String query = "INSERT INTO SPASAVATELJ(IME, BROJ_TELEFONA, SPECIJALNOST, ISKUSTVO, LOKACIJA_LAT, LOKACIJA_LNG, AKTIVAN, USERNAME, PWORD, IS_RESCUER) VALUES('"
            + rescuer.getIme() + "'," +
            " '" + rescuer.getBrojTelefona() + "'," +
            " '" + rescuer.getSpecijalnost().getValue() + "'," +
            " '" + rescuer.getIskustvo().getValue() + "'," +
            " '" + rescuer.getLokacija().getLat() + "'," +
            " '" + rescuer.getLokacija().getLng() + "'," +
            " '" + rescuer.isActive() + "'," +
            " '" + rescuer.getIme() + "'," +
            " '1234', TRUE)";
        jdbcTemplate.update(query);
    }

    public void saveUser(String username, String password, String phone) {
        String query = "INSERT INTO SPASAVATELJ(USERNAME, PWORD, BROJ_TELEFONA, IS_RESCUER) VALUES('"
            + username + "'," +
            " '" + password + "'," +
            " '" + phone + "', FALSE)";
        jdbcTemplate.update(query);
    }

    private Spasavatelj mapToSpasavatelj(Map<String, Object> dbRow) {
        Spasavatelj rescuer = new Spasavatelj();
        if (dbRow.get("ID") != null) {
            rescuer.setId((int) dbRow.get("ID"));
        }
        if (dbRow.get("IME") != null) {
            rescuer.setIme((String) dbRow.get("IME"));
        }
        if (dbRow.get("BROJ_TELEFONA") != null) {
            rescuer.setBrojTelefona((String) dbRow.get("BROJ_TELEFONA"));
        }
        if (dbRow.get("SPECIJALNOST") != null) {
            rescuer.setSpecijalnost(HgssSpecijalnost.getByValue((int) dbRow.get("SPECIJALNOST")));
        }
        if (dbRow.get("ISKUSTVO") != null) {
            rescuer.setIskustvo(HgssIskustvo.getByValue((int) dbRow.get("ISKUSTVO")));
        }
        if (dbRow.get("AKTIVAN") != null) {
            rescuer.setIsActive((boolean) dbRow.get("AKTIVAN"));
        }
        if (dbRow.get("LOKACIJA_LAT") != null && dbRow.get("LOKACIJA_LNG") != null) {
            rescuer.setLokacija(new HgssLocation((String) dbRow.get("LOKACIJA_LAT"), (String) dbRow.get("LOKACIJA_LNG")));
        }

        return rescuer;
    }

    public String getDeviceTokenById(Integer id) {
        String query = "SELECT DEV_TOKEN FROM SPASAVATELJ WHERE ID = " + id;
        return jdbcTemplate.queryForObject(query, String.class);
    }


    public void acceptAction(String userId, String actionId) {
        jdbcTemplate.update("UPDATE SPASAVATELJ_AKCIJA SET PRIHVATIO = TRUE WHERE ID_SPASAVATELJ = " + userId + "AND PRIHVATIO = FALSE" + " AND ID_AKCIJA = " + actionId);
    }

    public void refuseAction(String userId, String actionId) {
        jdbcTemplate.update("DELETE FROM SPASAVATELJ_AKCIJA WHERE ID_SPASAVATELJ = " + userId + " AND ID_AKCIJA = " + actionId);
    }

    public List<Spasavatelj> getUsersInActionByActionId(String actionId, boolean accepted) {
        String query = "SELECT * FROM SPASAVATELJ AS S JOIN SPASAVATELJ_AKCIJA AS SA ON SA.ID_SPASAVATELJ = S.ID WHERE ID_AKCIJA = " + actionId + " AND PRIHVATIO = " + accepted;
        List<Map<String, Object>> spasavatelji = jdbcTemplate.queryForList(query);

        List<Spasavatelj> retval = new ArrayList<>();

        spasavatelji.forEach(map -> retval.add(mapToSpasavatelj(map)));

        return retval;
    }

    public long getNumOfActiveUsers() {
        List<Map<String, Object>> inAction = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = SA.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID_A GROUP BY S.ID HAVING A.AKTIVNA = TRUE AND SA.PRIHVATIO = TRUE");

        List<Spasavatelj> action = new ArrayList<>();
        for (Map<String, Object> dbRow : inAction) {
            action.add(mapToSpasavatelj(dbRow));
        }

        return action.stream().count();
    }

    public void saveUserLocation(String userId, double lat, double lng, long timestamp) {
        String query = "INSERT INTO USER_LOCATION VALUES (" + userId + ", " + timestamp + ", '" + lat + "', '" + lng + "')";
        jdbcTemplate.update(query);
    }
}
