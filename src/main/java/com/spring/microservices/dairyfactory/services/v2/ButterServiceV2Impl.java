package com.spring.microservices.dairyfactory.services.v2;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.dairyfactory.web.controller.exception.ButterNotFoundException;
import com.spring.microservices.dairyfactory.web.mappers.ButterMapper;
import com.spring.microservices.model.v2.ButterDtoV2;
import com.spring.microservices.model.v2.ButterFlavourEnum;
import com.spring.microservices.model.v2.ButterPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ButterServiceV2Impl implements ButterServiceV2 {

    private ButterRepository butterRepository;
    private ButterMapper butterMapper;

    public ButterServiceV2Impl(ButterRepository butterRepository, ButterMapper butterMapper) {
        this.butterRepository = butterRepository;
        this.butterMapper = butterMapper;
    }

    @Cacheable(cacheNames = "butterCache", key = "#butterId", condition = "#showInventoryOnHand == false")
    @Override
    public ButterDtoV2 getButterById(UUID butterId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return butterMapper.butterToButterDtoWithInventory(butterRepository.findById(butterId).orElseThrow(ButterNotFoundException::new));
        } else {
            return butterMapper.butterToButterDtoV2(butterRepository.findById(butterId).orElseThrow(ButterNotFoundException::new));
        }

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
        butter.setFlavour(butterDtoV2.getFlavour());
        butter.setName(butterDtoV2.getName());
        butter.setPrice(butterDtoV2.getPrice());
        butter.setUpc(butterDtoV2.getUpc());

        Butter updatedButter = butterRepository.save(butter);
        return butterMapper.butterToButterDtoV2(updatedButter);
    }

    @Override
    public void deleteById(UUID butterId) {
        butterRepository.deleteById(butterId);
    }

    @Cacheable(cacheNames = "butterUpcCache", key = "#upc")
    @Override
    public ButterDtoV2 getButterByUpc(String upc) {
        return butterMapper.butterToButterDtoV2(butterRepository.findByUpc(upc));
    }

    @Cacheable(cacheNames = "butterListCache", condition = "#showInventoryOnHand == false")
    @Override
    public ButterPagedList listButter(String butterName, ButterFlavourEnum flavour, PageRequest pageRequest, Boolean showInventoryOnHand) {
        ButterPagedList butterPagedList;
        Page<Butter> butterPage;

        log.info("Executing listButter... ");

        if (!StringUtils.isEmpty(butterName) && !StringUtils.isEmpty(flavour)) {
            //search both
            butterPage = butterRepository.findAllByNameAndFlavour(butterName, flavour, pageRequest);
        } else if (!StringUtils.isEmpty(butterName) && StringUtils.isEmpty(flavour)) {
            //search butter name
            butterPage = butterRepository.findAllByName(butterName, pageRequest);
        } else if (StringUtils.isEmpty(butterName) && !StringUtils.isEmpty(flavour)) {
            //search butter style
            butterPage = butterRepository.findAllByFlavour(flavour, pageRequest);
        } else {
            butterPage = butterRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            butterPagedList = new ButterPagedList(butterPage
                                                          .getContent()
                                                          .stream()
                                                          .map(butterMapper::butterToButterDtoWithInventory)
                                                          .collect(Collectors.toList()),
                                                  PageRequest
                                                          .of(butterPage.getPageable().getPageNumber(),
                                                              butterPage.getPageable().getPageSize()),
                                                  butterPage.getTotalElements());
        } else {
            butterPagedList = new ButterPagedList(butterPage
                                                          .getContent()
                                                          .stream()
                                                          .map(butterMapper::butterToButterDtoV2)
                                                          .collect(Collectors.toList()),
                                                  PageRequest
                                                          .of(butterPage.getPageable().getPageNumber(),
                                                              butterPage.getPageable().getPageSize()),
                                                  butterPage.getTotalElements());
        }

        return butterPagedList;
    }
}
