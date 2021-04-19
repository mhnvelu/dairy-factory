package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        log.debug("Getting Customer : "+ customerId);
        return CustomerDto.builder().name("SpringGuru").customerId(customerId).build();
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.debug("Creating Customer : "+ customerDto.getName());
        return customerDto.builder().customerId(UUID.randomUUID()).build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        log.debug("Updating Customer : "+ customerId);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        log.debug("Deleting Customer : "+ customerId);
    }
}
