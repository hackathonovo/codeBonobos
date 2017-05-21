package io.codebonobos.controllers;

import io.codebonobos.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by afilakovic on 21.05.17..
 */
@RestController
@RequestMapping("/api/nfc")
public class NfcController {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseWrapper<?> getById(@PathVariable String id) {
        return new ResponseWrapper<>("http://np-risnjak.hr", null);
    }
}
