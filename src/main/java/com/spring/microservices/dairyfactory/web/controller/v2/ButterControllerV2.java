package com.spring.microservices.dairyfactory.web.controller.v2;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import com.spring.microservices.dairyfactory.web.services.v2.ButterServiceV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/butter")
@RestController
public class ButterControllerV2 {

    private final ButterServiceV2 butterServicev2;

    public ButterControllerV2(ButterServiceV2 butterServicev2) {
        this.butterServicev2 = butterServicev2;
    }


    @GetMapping({"/{butterId}"})
    public ResponseEntity<ButterDtoV2> getButter(@PathVariable("butterId") UUID butterId) {
        return new ResponseEntity<>(butterServicev2.getButterById(butterId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity saveButter(@RequestBody ButterDtoV2 butterDtoV2) {
        ButterDtoV2 savedButterDtoV2 = butterServicev2.saveButter(butterDtoV2);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v2/butter/" + savedButterDtoV2.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{butterId}"})
    public ResponseEntity updateButter(@PathVariable("butterId") UUID butterId,
                                       @RequestBody ButterDtoV2 butterDtoV2) {
        butterServicev2.updateButter(butterId, butterDtoV2);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{butterId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteButter(@PathVariable("butterId") UUID butterId) {
        butterServicev2.deleteById(butterId);
    }
}
