package com.tiket.poc.mongo.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zakyalvan
 */
public interface RegistrationService {
  Mono<Person> registerNew(@NotNull @Valid Person person);
  Flux<Person> streamAll();
  Flux<Person> bornOn(@NotNull LocalDate birthDate);
  Flux<Person> registeredOn(@NotNull LocalDate registeredDate);
}
