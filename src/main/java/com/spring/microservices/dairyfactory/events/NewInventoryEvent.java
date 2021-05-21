package com.spring.microservices.dairyfactory.events;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;

public class NewInventoryEvent extends ButterEvent {
    public NewInventoryEvent(ButterDtoV2 butterDtoV2) {
        super(butterDtoV2);
    }
}
