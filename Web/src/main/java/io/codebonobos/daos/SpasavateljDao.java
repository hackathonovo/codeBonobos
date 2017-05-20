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
        List<Map<String, Object>> available = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = SA.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID_A GROUP BY S.ID HAVING (A.AKTIVNA = FALSE OR A.AKTIVNA IS NULL) AND S.AKTIVAN = TRUE");
        List<Map<String, Object>> inAction = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = SA.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID_A GROUP BY S.ID HAVING A.AKTIVNA = TRUE");
        List<Map<String, Object>> inactive = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE AKTIVAN = FALSE");

        List<Spasavatelj> avail = new ArrayList<>();
        for (Map<String, Object> dbRow : available) {
            avail.add(mapToSpasavatelj(dbRow));
        }

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

    public Spasavatelj getByLogin(String username, String password) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE USERNAME = '" + username + "' AND PWORD = '" + password + "'");

        if (result == null || result.isEmpty()) {
            throw new Exception("No such user");
        }

        return mapToSpasavatelj(result.get(0));
    }

    public int saveFromLoginAndGetId(String username, String password) throws Exception {
        String query = "INSERT INTO SPASAVATELJ(USERNAME, PWORD) VALUES('" + username + "', '" + password + "')";
        jdbcTemplate.update(query);

        return (int) jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE USERNAME = '" + username + "'").get(0).get("ID");
    }

    public void saveRescuer(Spasavatelj rescuer) {
        String query = "INSERT INTO SPASAVATELJ(IME, BROJ_TELEFONA, SPECIJALNOST, ISKUSTVO, LOKACIJA_LAT, LOKACIJA_LNG, AKTIVAN, USERNAME, PWORD) VALUES('"
            + rescuer.getIme() + "'," +
            " '" + rescuer.getBrojTelefona() + "'," +
            " '" + rescuer.getSpecijalnost().getValue() + "'," +
            " '" + rescuer.getIskustvo().getValue() + "'," +
            " '" + rescuer.getLokacija().getLat() + "'," +
            " '" + rescuer.getLokacija().getLng() + "'," +
            " '" + rescuer.isActive() + "'," +
            " '" + rescuer.getIme() + "'," +
            " '1234')";
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


}
