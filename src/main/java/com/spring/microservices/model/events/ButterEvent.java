package com.spring.microservices.model.events;

import com.spring.microservices.model.v2.ButterDtoV2;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ButterEvent implements Serializable {
    private ButterDtoV2 butterDtoV2;
}
