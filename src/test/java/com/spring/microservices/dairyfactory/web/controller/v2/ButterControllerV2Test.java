package com.spring.microservices.dairyfactory.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservices.model.v2.ButterDtoV2;
import com.spring.microservices.model.v2.ButterFlavourEnum;
import com.spring.microservices.dairyfactory.services.v2.ButterServiceV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
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
                .price(new BigDecimal("2.99")).weightInGms(100).quantityOnHand(10).upc("12345").build();
    }

    @Test
    void getButter() throws Exception {

        given(butterServicev2.getButterById(any(), any())).willReturn(getValidButterDtoV2());

        mockMvc.perform(get("/api/v2/butter/{butterId}", UUID.randomUUID().toString())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("api/v2/butter-get",
                                pathParameters(parameterWithName("butterId").description("UUID of desired Butter to Get")),
                                responseFields(
                                        fieldWithPath("id").description("Id of Butter").type(UUID.class),
                                        fieldWithPath("version").description("Version Number").type(Integer.class),
                                        fieldWithPath("name").description("Butter Name"),
                                        fieldWithPath("weightInGms").description("Butter Weight In Gms"),
                                        fieldWithPath("price").description("Price of Butter"),
                                        fieldWithPath("upc").description("Universal Product Code"),
                                        fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                        fieldWithPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
                                        fieldWithPath("quantityOnHand").description("Quantity of Butter in Stock"),
                                        fieldWithPath("flavour").description("Butter Flavour, one of HONEY, GARLIC, HERB.").type(Enum.class)

                                )

                ));
    }

    @Test
    void saveButter() throws Exception {
        ButterDtoV2 butterDtoV2 = getValidButterDtoV2();

        String jsonPayload = objectMapper.writeValueAsString(butterDtoV2);

        butterDtoV2.setId(UUID.randomUUID());
        given(butterServicev2.saveButter(any())).willReturn(butterDtoV2);

        ConstrainedFields fields = new ConstrainedFields(ButterDtoV2.class);

        mockMvc.perform(post("/api/v2/butter/").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andDo(document("api/v2/butter-post",
                                requestFields(
                                        fields.withPath("id").ignored(),
                                        fields.withPath("version").ignored(),
                                        fields.withPath("name").description("Butter Name"),
                                        fields.withPath("weightInGms").description("Butter Weight In Gms"),
                                        fields.withPath("price").description("Price of Butter"),
                                        fields.withPath("upc").description("Universal Product Code"),
                                        fields.withPath("createdDate").ignored(),
                                        fields.withPath("lastModifiedDate").ignored(),
                                        fields.withPath("quantityOnHand").description("Quantity of Butter in Stock"),
                                        butterFlavourField(fields)
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

    private FieldDescriptor butterFlavourField(ConstrainedFields fields) {
        String formattedEnumValues = Arrays.stream(ButterFlavourEnum.values())
                .map(type -> String.format("`%s`", type))
                .collect(Collectors.joining(", "));
        return fields.withPath("flavour").description("Butter Flavour, one of " + formattedEnumValues + ".").type(Enum.class);
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                                                                                   .collectionToDelimitedString(this.constraintDescriptions
                                                                                                                        .descriptionsForProperty(path), ". ")));
        }
    }
}