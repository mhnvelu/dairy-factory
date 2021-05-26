package com.spring.microservices.dairyfactory.services;

import com.spring.microservices.model.ButterDto;

import java.util.UUID;

public interface ButterService {

    ButterDto getButterById(UUID butterId);

    ButterDto saveButter(ButterDto butterDto);

    void updateButter(UUID butterId, ButterDto butterDto);

    void deleteById(UUID butterId);
}
