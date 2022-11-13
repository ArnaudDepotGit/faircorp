package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.AdressSearchService;
import com.emse.spring.faircorp.dto.ApiGouvAdressDto;
import com.emse.spring.faircorp.dto.ApiGouvFeatureDto;
import com.emse.spring.faircorp.dto.ApiGouvResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(AdressSearchService.class)
public class AdressSearchServiceTest {
    @Autowired
    private AdressSearchService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void shouldFindAdresses() throws JsonProcessingException {
        // Arrange
        ApiGouvResponseDto expectedResponse = simulateApiResponse();

        String expectedUrl = UriComponentsBuilder
                .fromUriString("/search")
                .queryParam("q", "cours+fauriel")
                .queryParam("limit", 15)
                .build()
                .toUriString();

        this.server
                .expect(requestTo(expectedUrl))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expectedResponse), MediaType.APPLICATION_JSON));
        // Act
        List<ApiGouvAdressDto> adresses = this.service.findAdress(List.of("cours", "fauriel"));

        // Assert
        Assertions
                .assertThat(adresses)
                .hasSize(1)
                .extracting(ApiGouvAdressDto::getCity)
                .contains("Saint Etienne");
    }

    private ApiGouvResponseDto simulateApiResponse() {
        ApiGouvAdressDto expectedAdress = new ApiGouvAdressDto();
        expectedAdress.setCity("Saint Etienne");

        ApiGouvFeatureDto expectedFeature = new ApiGouvFeatureDto();
        expectedFeature.setProperties(expectedAdress);

        ApiGouvResponseDto expectedResponse = new ApiGouvResponseDto();
        expectedResponse.setFeatures(List.of(expectedFeature));

        return expectedResponse;
    }
}
