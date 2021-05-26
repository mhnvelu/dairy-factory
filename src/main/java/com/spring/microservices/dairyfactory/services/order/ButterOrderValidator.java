package com.spring.microservices.dairyfactory.services.order;

import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.model.ButterOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class ButterOrderValidator {

    private final ButterRepository butterRepository;

    public boolean validOrder(ButterOrderDto butterOrderDto) {
        AtomicInteger buttersNotFound = new AtomicInteger();

        butterOrderDto.getButterOrderLines().forEach(butterOrderLineDto -> {
            if (butterRepository.findByUpc(butterOrderLineDto.getUpc()) == null) {
                buttersNotFound.incrementAndGet();
            }
        });

        return buttersNotFound.get() == 0;
    }
}
