package com.spring.microservices.dairyfactory.web.services.v2;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.dairyfactory.web.controller.exception.ButterNotFoundException;
import com.spring.microservices.dairyfactory.web.mappers.ButterMapper;
import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ButterServiceV2Impl implements ButterServiceV2 {

    private ButterRepository butterRepository;
    private ButterMapper butterMapper;

    public ButterServiceV2Impl(ButterRepository butterRepository, ButterMapper butterMapper) {
        this.butterRepository = butterRepository;
        this.butterMapper = butterMapper;
    }

    @Override
    public ButterDtoV2 getButterById(UUID butterId) {
        return butterMapper.butterToButterDtoV2(butterRepository.findById(butterId).orElseThrow(ButterNotFoundException::new));
    }

    @Override
    public ButterDtoV2 saveButter(ButterDtoV2 butterDtoV2) {
        Butter butter = butterMapper.butterDtoV2ToButter(butterDtoV2);
        Butter savedButter = butterRepository.save(butter);
        return butterMapper.butterToButterDtoV2(savedButter);
    }

    @Override
    public ButterDtoV2 updateButter(UUID butterId, ButterDtoV2 butterDtoV2) {
        Butter butter = butterRepository.findById(butterId).orElseThrow(ButterNotFoundException::new);
        butter.setFlavour(butterDtoV2.getFlavour().name());
        butter.setName(butterDtoV2.getName());
        butter.setPrice(butterDtoV2.getPrice());
        butter.setQuantityInStock(butterDtoV2.getQuantityInStock());
        butter.setWeightInGms(butterDtoV2.getWeightInGms());

        Butter updatedButter = butterRepository.save(butter);
        return butterMapper.butterToButterDtoV2(updatedButter);
    }

    @Override
    public void deleteById(UUID butterId) {

    }
}
