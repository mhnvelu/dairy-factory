package com.spring.microservices.dairyfactory.events;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;

public class ProduceButterEvent extends ButterEvent {

    public ProduceButterEvent(ButterDtoV2 butterDtoV2) {
        super(butterDtoV2);
    }
}