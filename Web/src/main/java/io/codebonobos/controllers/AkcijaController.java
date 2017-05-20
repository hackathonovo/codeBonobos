package io.codebonobos.controllers;

import io.codebonobos.daos.AkcijaDao;
import io.codebonobos.entities.Akcija;
import io.codebonobos.entities.Spasavatelj;
import io.codebonobos.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */

@RestController
@RequestMapping("/api/akcije")
public class AkcijaController {
    @Autowired
    private AkcijaDao akcijaDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseWrapper<List<Akcija>> getAll() {
        List<Akcija> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseWrapper<List<Akcija>> getActive() {
        List<Akcija> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    public ResponseWrapper<List<Akcija>> getInactive() {
        List<Akcija> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseWrapper<Akcija> getActionByUserId(@PathVariable String id) {
        Akcija akcija = null;
        String message = null;

        return new ResponseWrapper<>(akcija, message);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public void addRescuer(@RequestBody Akcija action) {
        akcijaDao.saveAction(action);
    }
}
