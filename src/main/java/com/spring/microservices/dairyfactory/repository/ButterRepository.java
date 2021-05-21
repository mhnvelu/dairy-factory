package com.spring.microservices.dairyfactory.repository;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.web.model.v2.ButterFlavourEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ButterRepository extends JpaRepository<Butter, UUID> {

    Page<Butter> findAllByName(String butterName, Pageable pageable);

    Page<Butter> findAllByFlavour(ButterFlavourEnum flavour, Pageable pageable);

    Page<Butter> findAllByNameAndFlavour(String butterName, ButterFlavourEnum flavour, Pageable pageable);

    Butter findByUpc(String upc);
}
