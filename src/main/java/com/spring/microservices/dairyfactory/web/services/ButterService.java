package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.web.model.ButterDto;

import java.util.UUID;

public interface ButterService {

    ButterDto getButterById(UUID butterId);
}
