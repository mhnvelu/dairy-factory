package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.web.model.ButterDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ButterServiceImpl implements ButterService {
    @Override
    public ButterDto getButterById(UUID butterId) {
        return ButterDto.builder().id(UUID.randomUUID()).flavour("Honey").weightInGms(100)
                .name("ButterHoney").build();
    }
}
