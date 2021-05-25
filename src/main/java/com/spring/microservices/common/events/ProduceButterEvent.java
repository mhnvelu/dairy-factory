package com.spring.microservices.common.events;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProduceButterEvent extends ButterEvent {

    public ProduceButterEvent(ButterDtoV2 butterDtoV2) {
        super(butterDtoV2);
    }
}