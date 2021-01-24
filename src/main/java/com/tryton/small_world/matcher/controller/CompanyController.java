package com.tryton.small_world.matcher.controller;

import com.tryton.small_world.matcher.model.Company;
import com.tryton.small_world.matcher.model.CompanyCriteria;
import com.tryton.small_world.matcher.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Company>> getCompanies(@RequestParam(name = "customerId", required = false) Long customerId,
        @RequestParam(name = "name", required = false) String name) {
        CompanyCriteria companyCriteria = CompanyCriteria.builder()
            .customerId(customerId)
            .name(name)
            .build();

        Set<Company> companies = companyService.getByCriteria(companyCriteria);
        return companies.isEmpty()
            ? notFoundResponse()
            : successfulResponse(companies);
    }

    private static ResponseEntity<Set<Company>> notFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .build();
    }

    private static ResponseEntity<Set<Company>> successfulResponse(Set<Company> companies) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(companies);
    }
}
