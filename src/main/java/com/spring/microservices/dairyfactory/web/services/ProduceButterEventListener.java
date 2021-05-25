package com.spring.microservices.dairyfactory.web.services;

import com.spring.microservices.dairyfactory.config.JmsConfig;
import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.model.events.NewInventoryEvent;
import com.spring.microservices.model.events.ProduceButterEvent;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.model.v2.ButterDtoV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProduceButterEventListener {

    private final ButterRepository butterRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BUTTER_PRODUCE_QUEUE)
    public void listenProduceButterEvent(ProduceButterEvent produceButterEvent) {

        ButterDtoV2 butterDtoV2 = produceButterEvent.getButterDtoV2();
        log.info("Received ProduceButterEvent for butter : " + butterDtoV2.getName());
        Butter butter = butterRepository.getOne(butterDtoV2.getId());
        butterDtoV2.setQuantityOnHand(butter.getQuantityToProduce());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(butterDtoV2);
        log.info("Butter Name : " + butter.getName() + " MinOnHand : " + butter.getMinOnHand() + " InventoryQuantityOnHand : " +
                 butterDtoV2.getQuantityOnHand()
        );

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);

    }
}
