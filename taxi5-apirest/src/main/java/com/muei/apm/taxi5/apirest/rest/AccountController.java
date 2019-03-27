package com.muei.apm.taxi5.apirest.rest;

import com.muei.apm.taxi5.apirest.rest.dto.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AccountController {

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
        if (true) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
