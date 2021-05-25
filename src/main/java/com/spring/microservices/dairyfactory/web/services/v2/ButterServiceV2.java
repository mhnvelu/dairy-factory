package com.spring.microservices.dairyfactory.web.services.v2;

import com.spring.microservices.model.v2.ButterDtoV2;
import com.spring.microservices.model.v2.ButterFlavourEnum;
import com.spring.microservices.model.v2.ButterPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ButterServiceV2 {
    ButterDtoV2 getButterById(UUID butterId, Boolean showInventoryOnHand);

    ButterDtoV2 saveButter(ButterDtoV2 butterDto);

    ButterDtoV2 updateButter(UUID butterId, ButterDtoV2 butterDto);

    void deleteById(UUID butterId);

    ButterDtoV2 getButterByUpc(String upc);

    ButterPagedList listButter(String butterName, ButterFlavourEnum flavour, PageRequest pageRequest, Boolean showInventoryOnHand);
}
