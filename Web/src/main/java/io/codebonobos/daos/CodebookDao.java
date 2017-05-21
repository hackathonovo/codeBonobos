package io.codebonobos.daos;

import io.codebonobos.entities.Codebook;
import io.codebonobos.utils.CodebookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by afilakovic on 21.05.17..
 */
@Component
public class CodebookDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CodebookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertIntoTable(String tableName, String value) {
        jdbcTemplate.update("INSERT INTO " + tableName + "(VALUE) VALUES('" + value + "')");
    }

    public Codebook getAllFromTable(String tableName) {
        List<Map<String, Object>> entries = jdbcTemplate.queryForList("SELECT * FROM " + tableName);

        Codebook codebook = new Codebook();

        for (Map<String, Object> entry : entries) {
            CodebookEntry e = new CodebookEntry();
            e.setId((int) entry.get("ID_" + tableName.toUpperCase()));
            e.setValue((String) entry.get("VALUE"));

            codebook.add(e);
        }

        return codebook;
    }
}
