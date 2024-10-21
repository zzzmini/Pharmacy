package com.my.pharmacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@ToString
@Getter
@Setter
public class KakaoApiResponseDTO {
    @JsonProperty("meta")
    private MetaDTO metaDTO;
    @JsonProperty("documents")
    private List<DocumentDTO> documentList;
}
