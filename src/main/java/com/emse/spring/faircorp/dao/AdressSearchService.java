package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.dto.ApiGouvAdressDto;
import com.emse.spring.faircorp.dto.ApiGouvFeatureDto;
import com.emse.spring.faircorp.dto.ApiGouvResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdressSearchService {
    private final RestTemplate restTemplate;

    public AdressSearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("https://api-adresse.data.gouv.fr").build();
    }

    public List<ApiGouvAdressDto> findAdress(List<String> keys) {
        String params = String.join("+", keys);
        String uri = UriComponentsBuilder.fromUriString("/search")
                .queryParam("q", params)
                .queryParam("limit", 15)
                .build()
                .toUriString();
        List<ApiGouvFeatureDto> features = restTemplate.getForObject(uri, ApiGouvResponseDto.class).getFeatures();
        List<ApiGouvAdressDto> featureAdress = new ArrayList<>();
        for (ApiGouvFeatureDto feature : features) {
            featureAdress.add(feature.getProperties());
        }
        return featureAdress;
    }
}
