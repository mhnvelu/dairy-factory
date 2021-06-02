package com.spring.microservices.dairyfactory.services.v2;

import com.spring.microservices.model.inventory.ButterInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "dairy-factory-inventory-service")
public interface DairyFactoryInventoryServiceOpenFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = ButterInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<ButterInventoryDto>> getOnHandInventory(@PathVariable UUID butterId);
}
