package com.spring.microservices.dairyfactory.services.v2;

import java.util.UUID;

public interface ButterInventoryService {

    Integer getOnHandInventory(UUID butterId);
}
