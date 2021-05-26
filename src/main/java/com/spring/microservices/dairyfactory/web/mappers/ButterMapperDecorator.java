package com.spring.microservices.dairyfactory.web.mappers;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.model.v2.ButterDtoV2;
import com.spring.microservices.dairyfactory.services.v2.ButterInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ButterMapperDecorator implements ButterMapper {
    private ButterInventoryService butterInventoryService;
    private ButterMapper butterMapper;

    @Autowired
    public void setButterInventoryService(ButterInventoryService butterInventoryService) {
        this.butterInventoryService = butterInventoryService;
    }

    @Autowired
    public void setMapper(ButterMapper butterMapper) {
        this.butterMapper = butterMapper;
    }

    @Override
    public ButterDtoV2 butterToButterDtoV2(Butter butter) {
        return butterMapper.butterToButterDtoV2(butter);
    }

    @Override
    public ButterDtoV2 butterToButterDtoWithInventory(Butter butter) {
        ButterDtoV2 dto = butterMapper.butterToButterDtoV2(butter);
        dto.setQuantityOnHand(butterInventoryService.getOnHandInventory(butter.getId()));
        return dto;
    }

    @Override
    public Butter butterDtoV2ToButter(ButterDtoV2 butterDtoV2) {
        return butterMapper.butterDtoV2ToButter(butterDtoV2);
    }
}
