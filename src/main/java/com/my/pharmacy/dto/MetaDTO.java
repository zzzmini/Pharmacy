package com.my.pharmacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MetaDTO {
    @JsonProperty("total_count")
    private Integer totalCount;
}
