package com.befoodly.be.model.response;

import com.befoodly.be.model.Feedback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CookProfileResponse {
    Long id;
    String referenceId;
    Long vendorId;
    String name;
    String imgUrl;
    String description;
    Long orderCounts;
    List<String> specialities;
    Feedback feedback;
}
