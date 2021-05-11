package com.spring.microservices.dairyfactory.bootstrap;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.repository.ButterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

//@Component
public class DairyFactoryDataLoader implements CommandLineRunner {

    public static final String BUTTER_1_UPC = "0631234200036";
    public static final String BUTTER_2_UPC = "0631234300019";
    public static final String BUTTER_3_UPC = "0083783375213";
    public static final UUID BUTTER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BUTTER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BUTTER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final ButterRepository butterRepository;

    public DairyFactoryDataLoader(ButterRepository butterRepository) {
        this.butterRepository = butterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Loading Butter...");
//        if (butterRepository.count() == 0) {
//            butterRepository
//                    .save(Butter.builder().name("Butter Honey").weightInGms(100).flavour("Honey")
//                                  .price(new BigDecimal("10.99")).quantityToProduce(100).upc(BUTTER_1_UPC).minOnHand(12).build());
//
//            butterRepository
//                    .save(Butter.builder().name("Butter Garlic").weightInGms(50).flavour("Garlic")
//                                  .price(new BigDecimal("11.99")).quantityToProduce(200).upc(BUTTER_2_UPC).minOnHand(15).build());
//
//            butterRepository
//                    .save(Butter.builder().name("Butter Herb").weightInGms(50).flavour("Herb")
//                                  .price(new BigDecimal("12.99")).quantityToProduce(300).upc(BUTTER_3_UPC).minOnHand(20).build());
//        }

//        System.out.println("Loaded Butter : " + butterRepository.count());

    }
}
