package com.spring.microservices.dairyfactory.web.mappers;

import com.spring.microservices.dairyfactory.domain.Customer;
import com.spring.microservices.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);

}
