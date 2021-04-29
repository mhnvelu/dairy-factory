package com.spring.microservices.dairyfactory.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import com.spring.microservices.dairyfactory.web.model.v2.ButterFlavourEnum;
import com.spring.microservices.dairyfactory.web.services.v2.ButterServiceV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ButterControllerV2.class)
class ButterControllerV2Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ButterServiceV2 butterServicev2;

    ButterDtoV2 getValidButterDtoV2() {
        return ButterDtoV2.builder().name("My Butter").flavour(ButterFlavourEnum.HERB)
                .price(new BigDecimal("2.99")).weightInGms(100).quantityInStock(10).build();
    }

    @Test
    void getButter() throws Exception {

        given(butterServicev2.getButterById(any())).willReturn(getValidButterDtoV2());

        mockMvc.perform(get("/api/v2/butter/" + UUID.randomUUID().toString())
                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void saveButter() throws Exception {
        ButterDtoV2 butterDtoV2 = getValidButterDtoV2();

        String jsonPayload = objectMapper.writeValueAsString(butterDtoV2);

        butterDtoV2.setId(UUID.randomUUID());
        given(butterServicev2.saveButter(any())).willReturn(butterDtoV2);

        mockMvc.perform(post("/api/v2/butter/").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload)).andExpect(status().isCreated());
    }

    @Test
    void updateButter() throws Exception {

        ButterDtoV2 butterDtoV2 = getValidButterDtoV2();

        String jsonPayload = objectMapper.writeValueAsString(butterDtoV2);

        butterDtoV2.setId(UUID.randomUUID());
        given(butterServicev2.saveButter(any())).willReturn(butterDtoV2);

        mockMvc.perform(put("/api/v2/butter/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteButter() throws Exception {

        mockMvc.perform(delete("/api/v2/butter/" + UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());
    }
}