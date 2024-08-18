package com.example.jwt.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor/")
public class VisitorController {
    @GetMapping("getAll")
    public ResponseEntity<?> getAllVisitors(){
        return new ResponseEntity<>("Testing jwt", HttpStatus.OK);
    }
}
