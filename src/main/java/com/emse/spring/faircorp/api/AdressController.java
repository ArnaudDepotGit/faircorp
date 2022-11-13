package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.AdressSearchService;
import com.emse.spring.faircorp.dto.ApiGouvAdressDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // (1)
@RequestMapping("/api/address") // (2)
@Transactional // (3)
public class AdressController {
    private final AdressSearchService adressSearchService;

    public AdressController(AdressSearchService adressSearchService) {
        this.adressSearchService = adressSearchService;
    }

    @GetMapping
    public List<ApiGouvAdressDto> findAdress(@RequestBody String params) {
        return adressSearchService.findAdress(List.of(params.split("-")));
    }
}
