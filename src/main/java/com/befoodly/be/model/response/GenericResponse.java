package com.befoodly.be.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class GenericResponse<T> {
    Optional<T> data;
    Integer statusCode;
    String errorMessage;
}
