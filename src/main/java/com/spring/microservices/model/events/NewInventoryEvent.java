package com.spring.microservices.model.events;

import com.spring.microservices.model.v2.ButterDtoV2;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends ButterEvent {
    public NewInventoryEvent(ButterDtoV2 butterDtoV2) {
        super(butterDtoV2);
    }
}
