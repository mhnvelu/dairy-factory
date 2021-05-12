package com.spring.microservices.dairyfactory.web.services.v2;

import com.spring.microservices.dairyfactory.web.model.inventory.ButterInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "microservices.host", ignoreUnknownFields = true)
@Component
public class ButterInventoryServiceRestTemplateImpl implements ButterInventoryService {
    public static final String INVENTORY_PATH = "/api/v1/butter/{butterId}/inventory";
    private final RestTemplate restTemplate;

    private String dairyFactoryInventoryServiceHost;

    public ButterInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setDairyFactoryInventoryServiceHost(String dairyFactoryInventoryServiceHost) {
        this.dairyFactoryInventoryServiceHost = dairyFactoryInventoryServiceHost;
    }

    @Override
    public Integer getOnHandInventory(UUID butterId) {

        log.debug("Calling Inventory Service");

        ResponseEntity<List<ButterInventoryDto>> responseEntity = restTemplate
                .exchange(dairyFactoryInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                          new ParameterizedTypeReference<List<ButterInventoryDto>>() {
                          }, (Object) butterId);

        //sum from inventory list
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(ButterInventoryDto::getQuantityOnHand)
                .sum();

        return onHand;
    }
}