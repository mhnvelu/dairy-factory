package com.spring.microservices.dairyfactory.repository;

import com.spring.microservices.dairyfactory.domain.Butter;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ButterRepository extends PagingAndSortingRepository<Butter, UUID> {
}
