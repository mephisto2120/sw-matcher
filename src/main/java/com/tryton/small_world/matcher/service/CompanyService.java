package com.tryton.small_world.matcher.service;

import com.google.common.collect.Sets;
import com.tryton.small_world.matcher.model.Company;
import com.tryton.small_world.matcher.model.CompanyCriteria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Component
public class CompanyService {
    private final Map<Long, Company> companiesByCustomerId;
    private final Map<String, Company> companiesName;

    public CompanyService() {
        companiesByCustomerId = new HashMap<>();
        companiesName = new HashMap<>();

        long scyllaId = 123L;
        Company scylla = Company.builder()
            .customerId(scyllaId)
            .companyId(111L)
            .name("Scylla")
            .city("New York")
            .street("Washington")
            .localNumber("24")
            .email("scylla@gmail.com")
            .createdDate(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .build();

        long charibdisId = 234L;
        Company charibdis = Company.builder()
            .customerId(charibdisId)
            .companyId(222L)
            .name("Charibdis")
            .city("New York")
            .street("Franklin")
            .localNumber("24")
            .email("charibdies@gmail.com")
            .createdDate(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .build();

        companiesByCustomerId.put(scyllaId, scylla);
        companiesByCustomerId.put(charibdisId, charibdis);

        companiesName.put("Scylla", scylla);
        companiesName.put("Charibdis", charibdis);
    }

    public Set<Company> getByCriteria(CompanyCriteria companyCriteria) {
        Set<Company> companiesByCustomerId = getCompaniesByCustomerId(companyCriteria);
        Set<Company> companiesByName = getCompaniesByName(companyCriteria);

        if (companiesByCustomerId.isEmpty() || companiesByName.isEmpty()) {
            return Collections.emptySet();
        }

        return Sets.intersection(companiesByCustomerId, companiesByName);
    }

    private Set<Company> getCompaniesByCustomerId(CompanyCriteria companyCriteria) {
        if (companyCriteria.getCustomerId() == null) {
            return newHashSet(companiesByCustomerId.values());
        }

        if (companiesByCustomerId.containsKey(companyCriteria.getCustomerId())) {
            return Collections.singleton(companiesByCustomerId.get(companyCriteria.getCustomerId()));
        }

        return Collections.emptySet();
    }

    private Set<Company> getCompaniesByName(CompanyCriteria companyCriteria) {
        if (companyCriteria.getName() == null) {
            return newHashSet(companiesName.values());
        }

        if (companiesName.containsKey(companyCriteria.getName())) {
            return Collections.singleton(companiesName.get(companyCriteria.getName()));
        }

        return Collections.emptySet();
    }
}
