package com.spring.microservices.dairyfactory.web.controller;

import com.spring.microservices.dairyfactory.web.model.ButterDto;
import com.spring.microservices.dairyfactory.web.services.ButterService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Deprecated
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

    @PostMapping()
    public ResponseEntity saveButter(@Valid @RequestBody ButterDto butterDto) {
        ButterDto savedButterDto = butterService.saveButter(butterDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/butter/" + savedButterDto.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{butterId}"})
    public ResponseEntity updateButter(@PathVariable("butterId") UUID butterId,
                                       @Valid @RequestBody ButterDto butterDto) {
        butterService.updateButter(butterId, butterDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{butterId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteButter(@PathVariable("butterId") UUID butterId) {
        butterService.deleteById(butterId);
    }
}

