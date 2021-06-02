package com.spring.microservices.dairyfactory.services.v2;

import com.spring.microservices.model.inventory.ButterInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("local-service-discovery")
public class ButterInventoryServiceOpenFeignClientImpl implements ButterInventoryService {

    private final DairyFactoryInventoryServiceOpenFeignClient dairyFactoryInventoryServiceOpenFeignClient;

    @Override
    public Integer getOnHandInventory(UUID butterId) {
        log.info("Calling Inventory Service using OpenFeignClient..");
        ResponseEntity<List<ButterInventoryDto>> responseEntity =
                dairyFactoryInventoryServiceOpenFeignClient.getOnHandInventory(butterId);
        //sum from inventory list
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(ButterInventoryDto::getQuantityOnHand)
                .sum();
        log.info("ButterId : " + butterId + "on hand is " + onHand);
        return onHand;
    }
}
