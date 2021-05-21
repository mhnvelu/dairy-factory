package com.spring.microservices.dairyfactory.events;

import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class ButterEvent implements Serializable {
    private final ButterDtoV2 butterDtoV2;
}
