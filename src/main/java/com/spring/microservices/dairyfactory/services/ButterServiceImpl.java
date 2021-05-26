package com.spring.microservices.dairyfactory.services;

import com.spring.microservices.model.ButterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ButterServiceImpl implements ButterService {
    @Override
    public ButterDto getButterById(UUID butterId) {
        log.debug("Getting Butter : " + butterId);
        return ButterDto.builder().id(UUID.randomUUID()).flavour("Honey").weightInGms(100)
                .name("ButterHoney").build();
    }

    @Override
    public ButterDto saveButter(ButterDto butterDto) {
        log.debug("Creating Butter : " + butterDto.getName());
        return butterDto.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateButter(UUID butterId, ButterDto butterDto) {
        log.debug("Updating Butter : " + butterId);
    }

    @Override
    public void deleteById(UUID butterId) {
        log.debug("Deleting Butter : " + butterId);
    }


}
