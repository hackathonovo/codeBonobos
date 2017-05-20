package io.codebonobos.controllers;

import io.codebonobos.daos.SpasavateljDao;
import io.codebonobos.entities.Spasavatelj;
import io.codebonobos.utils.IdWrapper;
import io.codebonobos.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */
@RestController
@RequestMapping("/api/spasavatelji")
public class SpasavateljiController {
    @Autowired
    private SpasavateljDao spasavateljDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseWrapper<List<Spasavatelj>> getAll() {
        List<Spasavatelj> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/all-grouped", method = RequestMethod.GET)
    public ResponseWrapper<List<Spasavatelj>> getAllGroupedByType() {
        List<Spasavatelj> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseWrapper<List<Spasavatelj>> getActive() {
        List<Spasavatelj> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    public ResponseWrapper<List<Spasavatelj>> getInactive() {
        List<Spasavatelj> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/action", method = RequestMethod.GET)
    public ResponseWrapper<List<Spasavatelj>> getThoseInAction() {
        List<Spasavatelj> list = null;
        String message = null;

        return new ResponseWrapper<>(list, message);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseWrapper<Spasavatelj> getProfile(@PathVariable int id) {
        Spasavatelj spasavatelj = null;
        String message = null;
        try {
            spasavatelj = spasavateljDao.getById(id);
        } catch (Exception e) {
            message = e.getMessage();
        }
        return new ResponseWrapper<>(spasavatelj, message);
    }

    @RequestMapping(value = "/fb/{token}", method = RequestMethod.GET)
    public ResponseWrapper<IdWrapper> getIdByFbToken(@PathVariable String token, @RequestParam String name) throws Exception {
        IdWrapper id;
        String message = null;
        try {
            id = new IdWrapper(spasavateljDao.getByFbToken(token).getId());
        } catch (Exception e) {
            id = new IdWrapper(spasavateljDao.saveFromFbTokenAndGetId(name, token));
        }

        return new ResponseWrapper<>(id, message);
    }
}
