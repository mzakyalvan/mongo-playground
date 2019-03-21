package com.tiket.poc.mongo.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * @author zakyalvan
 */
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
  @Tailable
  Flux<Person> findPeopleByRegisteredTimeWithin(LocalDate registeredDate);

  Flux<Person> findPeopleByBirthDateEquals(LocalDate birthDate);

  Flux<Person> findPeopleByRegisteredTimeEquals(LocalDateTime registeredDate);
}
