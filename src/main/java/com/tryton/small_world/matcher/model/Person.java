package com.tryton.small_world.matcher.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class Person {
    private final long personId;
    private final long customerId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;
    private String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModified;
}
