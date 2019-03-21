package com.tiket.poc.mongo.date;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.BodyInserters.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SampleApplication.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RegistrationControllerTests {

	public static final Person CANDIDATE = Person.builder()
			.fullName("Muhammad Zaky Alvan")
			.emailAddress("zakyalvan@gmail.com")
			.birthDate(LocalDate.of(1985, Month.JUNE, 18))
			.build();

	@Autowired
	private WebTestClient testClient;

	@Test
	public void whenRegister_shouldSuccess() {
		testClient.post().uri("/registry")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(fromObject(CANDIDATE))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
					.jsonPath("id").isNotEmpty()
					.jsonPath("fullName").value(equalTo("Muhammad Zaky Alvan"))
					.jsonPath("emailAddress").value(equalTo("zakyalvan@gmail.com"))
					.jsonPath("birthDate").value(equalTo("1985-06-18"))
					.jsonPath("registeredTime").isNotEmpty();
	}

	public void whenRetrievingBirthDayPeople_thenShouldSuccess() {
		testClient.post().uri("/registry")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(fromObject(CANDIDATE))
				.exchange()
				.expectStatus().isOk();
	}
}
