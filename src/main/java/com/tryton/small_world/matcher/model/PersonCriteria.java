package com.tryton.small_world.matcher.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
@ToString
public class PersonCriteria {
    private Long customerId;
    private String firstName;
    private String lastName;
}
