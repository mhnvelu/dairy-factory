package com.spring.microservices.dairyfactory.web.services.v2;

import java.util.UUID;

public interface ButterInventoryService {

    Integer getOnHandInventory(UUID butterId);
}
