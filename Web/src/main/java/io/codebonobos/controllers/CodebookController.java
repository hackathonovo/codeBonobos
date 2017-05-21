package io.codebonobos.controllers;

import io.codebonobos.daos.CodebookDao;
import io.codebonobos.entities.Codebook;
import io.codebonobos.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by afilakovic on 21.05.17..
 */
@RestController
@RequestMapping("/api/codebook")
public class CodebookController {
    @Autowired
    private CodebookDao codebookDao;

    @RequestMapping(value = "/{tableName}/insert", method = RequestMethod.GET)
    public ResponseEntity<?> insertIntoTable(@PathVariable String tableName, @RequestParam String value) {

        try {
            codebookDao.insertIntoTable(tableName, value);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.GET)
    public ResponseEntity<?> getTable(@PathVariable String tableName) {
        Codebook codebook = null;
        String message = null;
        try {
            codebook = codebookDao.getAllFromTable(tableName);
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(codebook, message), HttpStatus.OK);
    }
}
