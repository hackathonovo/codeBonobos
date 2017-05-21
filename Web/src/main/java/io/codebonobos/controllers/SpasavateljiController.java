package io.codebonobos.controllers;

import io.codebonobos.daos.AkcijaDao;
import io.codebonobos.daos.SpasavateljDao;
import io.codebonobos.entities.Akcija;
import io.codebonobos.entities.Spasavatelj;
import io.codebonobos.utils.Haversine;
import io.codebonobos.utils.RescuerDistanceWrapper;
import io.codebonobos.utils.RescuerListsWrapper;
import io.codebonobos.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by afilakovic on 20.05.17..
 */
@RestController
@RequestMapping("/api/spasavatelji")
public class SpasavateljiController {
    @Autowired
    private SpasavateljDao spasavateljDao;

    @Autowired
    private AkcijaDao akcijaDao;

    @RequestMapping(value = "/all-grouped", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGroupedByType() {
        RescuerListsWrapper lists = null;
        String message = null;

        try {
            lists = spasavateljDao.getRescuersByGroups();
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(lists, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile(@PathVariable int id) {
        Spasavatelj spasavatelj = null;
        String message = null;
        try {
            spasavatelj = spasavateljDao.getById(id);
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseWrapper<>(spasavatelj, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> getIdFromLogin(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String firebaseToken) throws Exception {
        Spasavatelj spasavatelj;
        String message = null;
        try {
            spasavatelj = spasavateljDao.getByLogin(username, password, firebaseToken);
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(spasavatelj, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public void addRescuer(@RequestBody Spasavatelj user) {
        spasavateljDao.saveRescuer(user);
    }

    @RequestMapping(value = "/get-closest", method = RequestMethod.GET)
    public ResponseEntity<?> getClosestRescuers(@RequestParam String actionId) {
        Akcija action;
        List<Spasavatelj> rescuers;
        String message = null;

        try {
            action = akcijaDao.getActionById(String.valueOf(1));
            rescuers = spasavateljDao.getAvailable();
            if (action.getLocation().getLng() == null || action.getLocation().getLat() == null) {
                throw new Exception("Bad coordinates.");
            }
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        List<RescuerDistanceWrapper> sortedRdw = rescuers.stream()
            .filter(r -> r.getLokacija() != null)
            .map(r -> new RescuerDistanceWrapper(r, Haversine.distance(r.getLokacija(), action.getLocation())))
            .sorted((t0, t1) -> t0.getDistance() <= t1.getDistance() ? 0 : 1)
            .collect(Collectors.toList());

        return new ResponseEntity<>(new ResponseWrapper<>(sortedRdw, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/answer-invite", method = RequestMethod.GET)
    public ResponseEntity<?> actionInviteAnswer(@RequestParam boolean answer, @RequestParam String userId, @RequestParam String actionId) {
        try {
            if (answer) {
                spasavateljDao.acceptAction(userId, actionId);
            } else {
                spasavateljDao.refuseAction(userId, actionId);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(null, "Fail"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(null, "Success"), HttpStatus.OK);
    }

    @RequestMapping(value = "/location/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> rememberUserLocation(@PathVariable String userId, @RequestParam double lat, @RequestParam double lng, @RequestParam long timestamp) {
        try {
            spasavateljDao.saveUserLocation(userId, lat, lng, timestamp);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(null, "Success"), HttpStatus.OK);
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.GET)
    public ResponseEntity<?> saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String phone) {
        try {
            spasavateljDao.saveUser(username, password, phone);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/no-of-rescuers", method = RequestMethod.GET)
    public ResponseEntity<?> getNumOfRescuers(@RequestParam String id) {
        long count;
        try {
            count = spasavateljDao.getUsersInActionByActionId(id, true).stream().count();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(count, null), HttpStatus.OK);
    }

    @RequestMapping(value = "/no-of-active-rescuers", method = RequestMethod.GET)
    public ResponseEntity<?> getNumOfActiveRescuers() {
        long count;
        String message = null;
        try {
            count = spasavateljDao.getNumOfActiveUsers();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(14, null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(count, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/no-of-avail-rescuers", method = RequestMethod.GET)
    public ResponseEntity<?> getNumOfAvailableRescuers() {
        long count;
        try {
            count = spasavateljDao.getNumOfAvailUsers();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(36, null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(count, null), HttpStatus.OK);
    }

}
