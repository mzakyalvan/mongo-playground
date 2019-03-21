package com.tiket.poc.mongo.date;

import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author zakyalvan
 */
@RestController
@RequestMapping("/registry")
public class RegisterController {
  private final RegistrationService registrationService;

  public RegisterController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @PostMapping
  public Mono<Person> registerNew(@Valid @RequestBody Mono<Person> input) {
    return input
        .flatMap(person -> registrationService.registerNew(person)
            .subscribeOn(Schedulers.elastic())
        );
  }

//  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//  public Flux<Person> streamAll() {
//    return registrationService.streamAll();
//  }

  @GetMapping(params = "bornDate")
  public Flux<Person> birthDayPeople(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bornDate) {
    return registrationService.bornOn(bornDate);
  }

  @GetMapping(params = "registeredDate")
  public Flux<Person> registeredOn(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate registeredDate) {
    return registrationService.registeredOn(registeredDate);
  }
}
