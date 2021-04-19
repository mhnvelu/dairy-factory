package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomerById(UUID customerId);

    CustomerDto saveCustomer(CustomerDto customerDto);

    void updateCustomer(UUID customerId, CustomerDto customerDto);

    void deleteCustomer(UUID customerId);
}
