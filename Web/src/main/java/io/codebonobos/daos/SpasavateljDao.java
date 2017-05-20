package io.codebonobos.daos;

import io.codebonobos.entities.HgssLocation;
import io.codebonobos.entities.Spasavatelj;
import io.codebonobos.enums.HgssIskustvo;
import io.codebonobos.enums.HgssSpecijalnost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    //
    //    public Map<Integer, List<Spasavatelj>> getRescuersByGroups(String id) {
    //        List<Map<String, Object>> available = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = AS.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID GROUP BY S.ID HAVING (A.AKTIVNA = FALSE OR A.AKTIVNA IS NULL) AND S.AKTIVAN = TRUE");
    //        List<Map<String, Object>> inAction = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ AS S LEFT JOIN SPASAVATELJ_AKCIJA AS SA ON S.ID = AS.ID_SPASAVATELJ LEFT JOIN AKCIJA AS A ON SA.ID_AKCIJA = A.ID GROUP BY S.ID HAVING (A.AKTIVNA = FALSE OR A.AKTIVNA IS NULL) AND S.AKTIVAN = TRUE");
    //
    //        for (Map<String, Object> dbRow : available) {
    //
    //        }
    //    }

    public Spasavatelj getByFbToken(String fbToken) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE FB_TOKEN = '" + fbToken + "'");

        if (result == null || result.isEmpty()) {
            throw new Exception("No such user");
        }

        return mapToSpasavatelj(result.get(0));
    }

    public int saveFromFbTokenAndGetId(String name, String fbToken) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO SPASAVATELJ(IME, FB_TOKEN) VALUES('"+ name +"', '"+fbToken+ "')";
        jdbcTemplate.update(query);

        return (int) jdbcTemplate.queryForList("SELECT * FROM SPASAVATELJ WHERE FB_TOKEN = '" + fbToken + "'").get(0).get("ID");
    }

    private Spasavatelj mapToSpasavatelj(Map<String, Object> dbRow) {
        Spasavatelj rescuer = new Spasavatelj();
        rescuer.setId((int) dbRow.get("ID"));
        rescuer.setIme((String) dbRow.get("IME"));
        rescuer.setBrojTelefona((String) dbRow.get("BROJ_TELEFONA"));
        rescuer.setSpecijalnost(HgssSpecijalnost.getByValue((int) dbRow.get("SPECIJALNOST")));
        rescuer.setIskustvo(HgssIskustvo.getByValue((int) dbRow.get("ISKUSTVO")));
        rescuer.setActive((boolean) dbRow.get("AKTIVAN"));
        rescuer.setLokacija(new HgssLocation((String) dbRow.get("LOKACIJA_LAT"), (String) dbRow.get("LOKACIJA_LNG")));

        return rescuer;
    }


}
