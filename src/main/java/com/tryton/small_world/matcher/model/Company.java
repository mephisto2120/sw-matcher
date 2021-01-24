package com.tryton.small_world.matcher.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Company {
    private final long companyId;
    private final long customerId;
    private final String name;
    private String city;
    private String zipCode;
    private String street;
    private String localNumber;
    private String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModified;
}
