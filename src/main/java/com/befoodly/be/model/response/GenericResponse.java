package com.befoodly.be.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class GenericResponse<T> {
    T data;
    Integer statusCode;
    String errorMessage;
}
