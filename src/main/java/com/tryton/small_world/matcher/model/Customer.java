package com.tryton.small_world.matcher.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class Customer {
    private final long customerId;
    private final Long personId;
    private final Long companyId;
    private final boolean isCompany;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModified;

    public static Customer createCompany(long customerId, long companyId, LocalDateTime createdDate, LocalDateTime lastModified) {
        return commonAttributesBuilder(customerId, createdDate, lastModified)
            .companyId(companyId)
            .personId(null)
            .isCompany(true)
            .build();
    }

    public static Customer createPerson(long customerId, long personId, LocalDateTime createdDate, LocalDateTime lastModified) {
        return commonAttributesBuilder(customerId, createdDate, lastModified)
            .companyId(null)
            .personId(personId)
            .isCompany(false)
            .build();
    }

    private static Customer.CustomerBuilder commonAttributesBuilder(long customerId, LocalDateTime createdDate, LocalDateTime lastModified) {
        return Customer.builder()
            .customerId(customerId)
            .createdDate(createdDate)
            .lastModified(lastModified);
    }
}
