package com.spring.microservices.dairyfactory.bootstrap;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import com.spring.microservices.dairyfactory.web.model.v2.ButterFlavourEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DairyFactoryDataLoader implements CommandLineRunner {

    private final ButterRepository butterRepository;

    public DairyFactoryDataLoader(ButterRepository butterRepository) {
        this.butterRepository = butterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Butter...");
        if (butterRepository.count() == 0) {
            butterRepository.save(Butter.builder().name("Butter Honey").weightInGms(100)
                                          .flavour(ButterFlavourEnum.HONEY)
                                          .price(new BigDecimal("10.99")).quantityInStock(100)
                                          .build());

            butterRepository.save(Butter.builder().name("Butter Garlic").weightInGms(50)
                                          .flavour(ButterFlavourEnum.GARLIC)
                                          .price(new BigDecimal("11.99")).quantityInStock(200)
                                          .build());
        }

        System.out.println("Loaded Butter : " + butterRepository.count());

    }
}
