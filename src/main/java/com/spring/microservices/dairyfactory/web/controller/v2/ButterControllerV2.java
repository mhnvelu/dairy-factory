package com.spring.microservices.dairyfactory.web.controller.v2;

import com.spring.microservices.model.v2.ButterDtoV2;
import com.spring.microservices.model.v2.ButterFlavourEnum;
import com.spring.microservices.model.v2.ButterPagedList;
import com.spring.microservices.dairyfactory.web.services.v2.ButterServiceV2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/v2/")
@RestController
public class ButterControllerV2 {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final ButterServiceV2 butterServicev2;

    public ButterControllerV2(ButterServiceV2 butterServicev2) {
        this.butterServicev2 = butterServicev2;
    }


    @GetMapping({"butter/{butterId}"})
    public ResponseEntity<ButterDtoV2> getButter(@PathVariable("butterId") UUID butterId, @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(butterServicev2.getButterById(butterId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("butterUpc/{upc}")
    public ResponseEntity<ButterDtoV2> getButterByUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(butterServicev2.getButterByUpc(upc), HttpStatus.OK);
    }

    @GetMapping(produces = {"application/json"}, path = "butter")
    public ResponseEntity<ButterPagedList> listButter(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "flavour", required = false) ButterFlavourEnum flavour,
                                                      @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ButterPagedList butterPagedList = butterServicev2.listButter(name, flavour, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(butterPagedList, HttpStatus.OK);
    }


    @PostMapping(path = "butter")
    public ResponseEntity saveButter(@Valid @RequestBody ButterDtoV2 butterDtoV2) {
        ButterDtoV2 savedButterDtoV2 = butterServicev2.saveButter(butterDtoV2);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v2/butter/" + savedButterDtoV2.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"butter/{butterId}"})
    public ResponseEntity updateButter(@PathVariable("butterId") UUID butterId,
                                       @Valid @RequestBody ButterDtoV2 butterDtoV2) {
        butterServicev2.updateButter(butterId, butterDtoV2);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"butter/{butterId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteButter(@PathVariable("butterId") UUID butterId) {
        butterServicev2.deleteById(butterId);
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e) {
//
//        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
//        e.getConstraintViolations().forEach(constraintViolation -> {
//            errors.add(constraintViolation.getPropertyPath() + " : " +
//                       constraintViolation.getMessage());
//        });
//
//        return new ResponseEntity<List>(errors, HttpStatus.BAD_REQUEST);
//    }
}
