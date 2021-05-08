package com.spring.microservices.dairyfactory.web.services.v2;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;

import java.util.UUID;

public interface ButterServiceV2 {
    ButterDtoV2 getButterById(UUID butterId);

    ButterDtoV2 saveButter(ButterDtoV2 butterDto);

    ButterDtoV2 updateButter(UUID butterId, ButterDtoV2 butterDto);

    void deleteById(UUID butterId);
}
