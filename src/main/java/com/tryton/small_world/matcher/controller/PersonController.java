package com.tryton.small_world.matcher.controller;

import com.tryton.small_world.matcher.model.Person;
import com.tryton.small_world.matcher.model.PersonCriteria;
import com.tryton.small_world.matcher.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Person>> getPersons(@RequestParam(name = "customerId", required = false) Long customerId,
        @RequestParam(name = "firstName", required = false) String firstName, @RequestParam(name = "lastName", required = false) String lastName) {
        PersonCriteria personCriteria = PersonCriteria.builder()
            .customerId(customerId)
            .firstName(firstName)
            .lastName(lastName)
            .build();

        Set<Person> persons = personService.getByCriteria(personCriteria);
        return persons.isEmpty()
            ? notFoundResponse()
            : successfulResponse(persons);
    }

    private static ResponseEntity<Set<Person>> notFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .build();
    }

    private static ResponseEntity<Set<Person>> successfulResponse(Set<Person> persons) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(persons);
    }
}
