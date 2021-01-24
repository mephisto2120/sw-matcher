package com.tryton.small_world.matcher.service;

import com.google.common.collect.Sets;
import com.tryton.small_world.matcher.model.Person;
import com.tryton.small_world.matcher.model.PersonCriteria;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Component
public class PersonService {
    private final Map<Long, Person> personsByCustomerId;
    private final Map<String, Person> personsByFirstName;
    private final Map<String, Person> personsByLastName;

    public PersonService() {
        personsByCustomerId = new HashMap<>();
        personsByFirstName = new HashMap<>();
        personsByLastName = new HashMap<>();
        String johnFirstName = "John";
        String johnLastName = "Debtor";
        Person john = Person.builder()
            .customerId(987L)
            .personId(999L)
            .firstName(johnFirstName)
            .lastName(johnLastName)
            .birthDate(LocalDate.of(1970, 2, 4))
            .city("New York")
            .street("Washington")
            .houseNumber("24")
            .email("john.debtor@gmail.com")
            .createdDate(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .build();

        String cloeFirstName = "Cloe";
        String loanLastName = "Loan";
        Person cloe = Person.builder()
            .customerId(876L)
            .personId(888L)
            .firstName(cloeFirstName)
            .lastName(loanLastName)
            .birthDate(LocalDate.of(1970, 2, 4))
            .city("New York")
            .street("Franklin")
            .houseNumber("24")
            .email("cloe.loan@gmail.com")
            .createdDate(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .build();

        personsByCustomerId.put(987L, john);
        personsByCustomerId.put(876L, cloe);

        personsByFirstName.put(johnFirstName, john);
        personsByFirstName.put(cloeFirstName, cloe);

        personsByLastName.put(johnLastName, john);
        personsByLastName.put(loanLastName, cloe);
    }

    public Set<Person> getByCriteria(PersonCriteria personCriteria) {
        Set<Person> personsByCustomerId = getPersonsByCustomerId(personCriteria);
        Set<Person> personsByFirstName = getPersonsByFirstName(personCriteria);
        Set<Person> personsByLastName = getPersonsByLastName(personCriteria);

        if (personsByCustomerId.isEmpty() || personsByFirstName.isEmpty() || personsByLastName.isEmpty()) {
            return Collections.emptySet();
        }

        return Sets.intersection(Sets.intersection(personsByCustomerId, personsByFirstName), personsByLastName);
    }

    private Set<Person> getPersonsByFirstName(PersonCriteria personCriteria) {
        if (personCriteria.getFirstName() == null) {
            return newHashSet(personsByFirstName.values());
        }

        if (personsByFirstName.containsKey(personCriteria.getFirstName())) {
            return newHashSet(personsByFirstName.get(personCriteria.getFirstName()));
        }
        return Collections.emptySet();
    }

    private Set<Person> getPersonsByLastName(PersonCriteria personCriteria) {
        if (personCriteria.getLastName() == null) {
            return newHashSet(personsByLastName.values());
        }

        if (personsByLastName.containsKey(personCriteria.getLastName())) {
            return newHashSet(personsByLastName.get(personCriteria.getLastName()));
        }
        return Collections.emptySet();
    }

    private Set<Person> getPersonsByCustomerId(PersonCriteria personCriteria) {
        if (personCriteria.getCustomerId() == null) {
            return newHashSet(personsByCustomerId.values());
        }

        if (personsByCustomerId.containsKey(personCriteria.getCustomerId())) {
            return Collections.singleton(personsByCustomerId.get(personCriteria.getCustomerId()));
        }
        return Collections.emptySet();
    }
}
