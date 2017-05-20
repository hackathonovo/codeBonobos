package io.codebonobos.controllers;

import static io.codebonobos.firebase.AndroidPushNotificationsService.FIREBASE_SERVER_KEY;

import io.codebonobos.daos.AkcijaDao;
import io.codebonobos.entities.Akcija;
import io.codebonobos.firebase.AndroidPushNotificationsService;
import io.codebonobos.firebase.FirebaseResponse;
import io.codebonobos.utils.IdWrapper;
import io.codebonobos.utils.ResponseWrapper;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by afilakovic on 20.05.17..
 */

@RestController
@RequestMapping("/api/akcije")
public class AkcijaController {

    private static final Logger log = Logger.getLogger(AkcijaController.class);

    @Autowired
    private AkcijaDao akcijaDao;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<Akcija> list;
        String message = null;

        try {
            list = akcijaDao.getAll();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(list, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseEntity<?> getActive() {
        List<Akcija> list;
        String message = null;

        try {
            list = akcijaDao.getActive();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(list, message), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    //    public ResponseWrapper<List<Akcija>> getInactive() {
    //        List<Akcija> list = null;
    //        String message = null;
    //
    //        return new ResponseWrapper<>(list, message);
    //    }

    //    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    //    public ResponseWrapper<Akcija> getActionByUserId(@PathVariable String id) {
    //        Akcija akcija = null;
    //        String message = null;
    //
    //        return new ResponseWrapper<>(akcija, message);
    //    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public ResponseEntity addAction(@RequestBody Akcija action) {
        IdWrapper id;
        String message = null;
        try {
            id = new IdWrapper(akcijaDao.saveAction(action));
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(id, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/end", method = RequestMethod.GET)
    public void finishAction(@RequestParam String actionId) {
        akcijaDao.finishAction(actionId);
    }

    @RequestMapping(value = "/active/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getActiveActionByUserId(@PathVariable String userId) {
        Akcija akcija;
        String message = null;
        try {
            akcija = akcijaDao.getActiveActionById(userId);
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseWrapper<>(akcija, message), HttpStatus.OK);
    }

    @RequestMapping(value = "/invite-rescuers/{actionId}", method = RequestMethod.POST)
    public ResponseEntity<?> inviteUsers(@RequestBody List<Integer> userIds, @PathVariable String actionId) throws JSONException {
        Akcija akcija;
        String message = null;
        try {
            akcijaDao.addInvitedRescuers(actionId, userIds);
        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        JSONObject body = new JSONObject();
        body.put("to", FIREBASE_SERVER_KEY);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("body", "body string here");
        notification.put("title", "title string here");

        JSONObject data = new JSONObject();
        data.put("key1", "value1");
        data.put("key2", "value2");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            FirebaseResponse firebaseResponse = pushNotification.get();
            if (firebaseResponse.getSuccess() == 1) {
                log.info("push notification sent ok!");
            } else {
                log.error("error sending push notifications: " + firebaseResponse.toString());
            }
            return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
    }
}
