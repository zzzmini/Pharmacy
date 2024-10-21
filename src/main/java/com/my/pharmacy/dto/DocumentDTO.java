package com.my.pharmacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DocumentDTO {
    // 약국이름
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("y")
    private double latitude;
    @JsonProperty("x")
    private double longitude;
    // 거리
    @JsonProperty("distance")
    private double distance;
}
