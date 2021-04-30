package com.spring.microservices.dairyfactory.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservices.dairyfactory.web.model.v2.ButterDtoV2;
import com.spring.microservices.dairyfactory.web.model.v2.ButterFlavourEnum;
import com.spring.microservices.dairyfactory.web.services.v2.ButterServiceV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dairyfactory.com", uriPort = 80)
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

        mockMvc.perform(get("/api/v2/butter/{butterId}", UUID.randomUUID().toString())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("/api/v2/butter/",
                                pathParameters(parameterWithName("butterId").description("UUID of desired Butter to Get")),
                                responseFields(
                                        fieldWithPath("id").description("Id of Butter"),
                                        fieldWithPath("version").description("Version Number"),
                                        fieldWithPath("name").description("Butter Name"),
                                        fieldWithPath("weightInGms").description("Butter Weight In Gms"),
                                        fieldWithPath("price").description("Price of Butter"),
                                        fieldWithPath("createdDate").description("Date Created"),
                                        fieldWithPath("lastModifiedDate").description("Date Updated"),
                                        fieldWithPath("quantityInStock").description("Quantity of Butter in Stock"),
                                        fieldWithPath("flavour").description("Butter Flavour")

                                )

                ));
    }

    @Test
    void saveButter() throws Exception {
        ButterDtoV2 butterDtoV2 = getValidButterDtoV2();

        String jsonPayload = objectMapper.writeValueAsString(butterDtoV2);

        butterDtoV2.setId(UUID.randomUUID());
        given(butterServicev2.saveButter(any())).willReturn(butterDtoV2);

        mockMvc.perform(post("/api/v2/butter/").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andDo(document("/api/v2/butter",
                                requestFields(
                                        fieldWithPath("id").ignored(),
                                        fieldWithPath("version").ignored(),
                                        fieldWithPath("name").description("Butter Name"),
                                        fieldWithPath("weightInGms").description("Butter Weight In Gms"),
                                        fieldWithPath("price").description("Price of Butter"),
                                        fieldWithPath("createdDate").ignored(),
                                        fieldWithPath("lastModifiedDate").ignored(),
                                        fieldWithPath("quantityInStock").description("Quantity of Butter in Stock"),
                                        fieldWithPath("flavour").description("Butter Flavour")
                                )
                ));
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