package com.spring.microservices.dairyfactory.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ButterDto {

    @Null
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String flavour;

    @Positive
    private int weightInGms;
}
