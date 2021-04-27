package com.spring.microservices.dairyfactory.web.services.v2;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ButterServiceV2Impl implements ButterServiceV2 {
    @Override
    public ButterDtoV2 getButterById(UUID butterId) {
        return null;
    }

    @Override
    public ButterDtoV2 saveButter(ButterDtoV2 butterDtoV2) {

        ButterDtoV2 tmp =  butterDtoV2.builder().id(UUID.randomUUID()).build();
        return tmp;
    }

    @Override
    public void updateButter(UUID butterId, ButterDtoV2 butterDtoV2) {

    }

    @Override
    public void deleteById(UUID butterId) {

    }
}
