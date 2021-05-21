package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.config.JmsConfig;
import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.events.ProduceButterEvent;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.dairyfactory.web.mappers.ButterMapper;
import com.spring.microservices.dairyfactory.web.services.v2.ButterInventoryServiceRestTemplateImpl;
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
            log.info("Butter Name :%s, MinOnHand :%d, InventoryQuantityOnHand :%d", butter.getName(),
                     butter.getMinOnHand(), inventoryQuantityOnHand);
            if (butter.getMinOnHand() >= inventoryQuantityOnHand) {
                jmsTemplate.convertAndSend(JmsConfig.BUTTER_PRODUCE_QUEUE,
                                           new ProduceButterEvent(butterMapper.butterToButterDtoV2(butter)));
            }
        });
    }
}
