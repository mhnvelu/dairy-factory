package com.spring.microservices.dairyfactory.services;

import com.spring.microservices.dairyfactory.config.JmsConfig;
import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.model.events.ProduceButterEvent;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.dairyfactory.web.mappers.ButterMapper;
import com.spring.microservices.dairyfactory.services.v2.ButterInventoryServiceRestTemplateImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DairyFactoryService {
    private final ButterInventoryServiceRestTemplateImpl butterInventoryServiceRestTemplate;
    private final ButterRepository butterRepository;
    private final JmsTemplate jmsTemplate;
    private final ButterMapper butterMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory() {
        List<Butter> butterList = butterRepository.findAll();
        butterList.forEach(butter -> {
            Integer inventoryQuantityOnHand = butterInventoryServiceRestTemplate.getOnHandInventory(butter.getId());
            log.info("Butter Name : " + butter.getName() + " MinOnHand : " + butter.getMinOnHand() + " InventoryQuantityOnHand " +
                     ": " +
                     inventoryQuantityOnHand
            );
            if (butter.getMinOnHand() >= inventoryQuantityOnHand) {
                log.info("Sending ProduceButterEvent for butter : " + butter.getName());
                jmsTemplate.convertAndSend(JmsConfig.BUTTER_PRODUCE_QUEUE,
                                           new ProduceButterEvent(butterMapper.butterToButterDtoV2(butter)));
            }
        });
    }
}
