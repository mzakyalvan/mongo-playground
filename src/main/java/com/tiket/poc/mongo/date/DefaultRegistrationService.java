package com.tiket.poc.mongo.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * @author zakyalvan
 */
@Service
@Validated
public class DefaultRegistrationService implements RegistrationService {
  private final PersonRepository personRepository;

  private final ReactiveMongoTemplate mongoTemplate;

  public DefaultRegistrationService(PersonRepository personRepository, ReactiveMongoTemplate mongoTemplate) {
    this.personRepository = personRepository;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Mono<Person> registerNew(Person person) {
    return personRepository.save(person);
  }

  @Override
  public Flux<Person> streamAll() {
    return mongoTemplate.changeStream("person", ChangeStreamOptions.builder().build() ,Person.class)
        .map(ChangeStreamEvent::getBody);
  }

  @Override
  public Flux<Person> bornOn(LocalDate birthDate) {
    return personRepository.findPeopleByBirthDateEquals(birthDate);
  }

  @Override
  public Flux<Person> registeredOn(LocalDate registeredDate) {
    return personRepository.findPeopleByRegisteredTimeEquals(LocalDateTime.of(registeredDate, LocalTime.MIDNIGHT));
  }
}
