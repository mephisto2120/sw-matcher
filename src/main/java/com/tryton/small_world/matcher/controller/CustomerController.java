package com.tryton.small_world.matcher.controller;

import com.tryton.small_world.matcher.model.Customer;
import com.tryton.small_world.matcher.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getInfo(@RequestParam(name = "customerId") long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return customer == null
            ? notFoundResponse()
            : successfulResponse(customer);
    }

    private static ResponseEntity<Customer> notFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .build();
    }

    private static ResponseEntity<Customer> successfulResponse(Customer customer) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(customer);
    }
}
