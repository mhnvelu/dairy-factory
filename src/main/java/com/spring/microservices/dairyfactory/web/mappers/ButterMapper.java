package com.spring.microservices.dairyfactory.web.mappers;

import com.spring.microservices.dairyfactory.domain.Butter;
import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ButterMapper {

    ButterDtoV2 butterToButterDtoV2(Butter butter);

    Butter butterDtoV2ToButter(ButterDtoV2 butterDto);

}
