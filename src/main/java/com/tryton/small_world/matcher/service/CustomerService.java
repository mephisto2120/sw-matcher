package com.tryton.small_world.matcher.service;

import com.tryton.small_world.matcher.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.tryton.small_world.matcher.model.Customer.createCompany;
import static com.tryton.small_world.matcher.model.Customer.createPerson;

@Component
public class CustomerService {

    private final Map<Long, Customer> customers;

    public CustomerService() {
        customers = new HashMap<>();
        customers.put(123L, createCompany(123L, 111L, LocalDateTime.now(), LocalDateTime.now()));
        customers.put(234L, createCompany(234L, 222L, LocalDateTime.now(), LocalDateTime.now()));
        customers.put(987L, createPerson(987L, 999L, LocalDateTime.now(), LocalDateTime.now()));
        customers.put(876L, createPerson(876L, 888L, LocalDateTime.now(), LocalDateTime.now()));
    }

    public Customer getCustomer(long customerId) {
        return customers.get(customerId);
    }
}
