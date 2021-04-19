package com.spring.microservices.dairyfactory.web.controller;

import com.spring.microservices.dairyfactory.web.model.ButterDto;
import com.spring.microservices.dairyfactory.web.services.ButterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/v1/butter")
@RestController
public class ButterController {

    private final ButterService butterService;

    public ButterController(ButterService butterService) {
        this.butterService = butterService;
    }


    @GetMapping({"/{butterId}"})
    public ResponseEntity<ButterDto> getButter(@PathVariable("butterId") UUID butterId) {
        return new ResponseEntity<>(butterService.getButterById(butterId), HttpStatus.OK);
    }
}

