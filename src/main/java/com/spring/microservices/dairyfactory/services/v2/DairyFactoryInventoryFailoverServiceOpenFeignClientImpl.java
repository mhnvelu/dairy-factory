package com.spring.microservices.dairyfactory.services.v2;

import com.spring.microservices.model.inventory.ButterInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class DairyFactoryInventoryFailoverServiceOpenFeignClientImpl implements DairyFactoryInventoryServiceOpenFeignClient {

    private final DairyFactoryInventoryFailoverServiceOpenFeignClient failoverServiceOpenFeignClient;

    @Override
    public ResponseEntity<List<ButterInventoryDto>> getOnHandInventory(UUID butterId) {
        log.info("Calling Inventory FailoverService using OpenFeignClient..");
        return failoverServiceOpenFeignClient.getOnHandInventory();
    }
}
