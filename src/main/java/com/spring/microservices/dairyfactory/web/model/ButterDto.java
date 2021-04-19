package com.spring.microservices.dairyfactory.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ButterDto {

    private UUID id;
    private String name;
    private String flavour;
    private int weightInGms;
}
