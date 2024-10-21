package com.my.pharmacy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OutputDTO {
    private String pharmacyName; //약국이름
    private String pharmacyAddress; //약국 주소
    private String directionURL; // 길안내  Url
    private String roadViewURL;  // 로드뷰 Url
    private String distance; // 약국주소 거리
}
