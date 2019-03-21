package com.tiket.poc.mongo.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zakyalvan
 */
@Getter
@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = Person.Builder.class)
@Document(collection = "person")
public class Person implements Serializable {

  @Id
  private String id;

  @NotBlank
  private String fullName;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Email
  @NotBlank
  private String emailAddress;

  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime registeredTime;

  @lombok.Builder(builderClassName = "Builder")
  public Person(String fullName, LocalDate birthDate, String emailAddress) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.emailAddress = emailAddress;
  }

  @PersistenceConstructor
  protected Person(String id, String fullName, LocalDate birthDate, String emailAddress, LocalDateTime registeredTime) {
    this.id = id;
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.emailAddress = emailAddress;
    this.registeredTime = registeredTime;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Builder birthDate(LocalDate birthDate) {
      this.birthDate = birthDate;
      return this;
    }
  }
}
