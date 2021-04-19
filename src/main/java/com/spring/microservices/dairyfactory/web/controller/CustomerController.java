package com.spring.microservices.dairyfactory.web.controller;

import com.spring.microservices.dairyfactory.web.model.CustomerDto;
import com.spring.microservices.dairyfactory.web.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> getCustomerById(
            @PathVariable("customerId") UUID customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomerDto = customerService.saveCustomer(customerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders
                .add("Location", "/api/v1/customer/" + savedCustomerDto.getCustomerId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{customerId}"})
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId,
                                         @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(customerId, customerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{customerId}"})
    public ResponseEntity deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
