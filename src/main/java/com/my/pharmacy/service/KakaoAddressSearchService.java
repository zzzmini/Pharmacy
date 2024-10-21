package com.my.pharmacy.service;

import com.my.pharmacy.dto.KakaoApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class KakaoAddressSearchService {
    // 환경변수에서 ${KAKAO_REST_API_KEY} 값을 가져와서 저장
    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private final RestTemplate restTemplate;

    // uri 만들기
    // https://dapi.kakao.com/v2/local/search/address.json?query=인사동길 12
    private static final String KAKAO_LOCAL_URL =
            "https://dapi.kakao.com/v2/local/search/address.json";

    public KakaoAddressSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public KakaoApiResponseDTO requestAddressSearch
            (String address) {
//        System.out.println("{kakao.rest.api.key} : " + kakaoRestApiKey);
        if(ObjectUtils.isEmpty(address)) return null;

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromHttpUrl(
                        KAKAO_LOCAL_URL
                );
        uriBuilder.queryParam("query", address);
        // 해석 불가능한 애들 UTF-8 인코딩
        URI uri = uriBuilder.build().encode().toUri();
        log.info("address : {}, uri : {}", address, uri);
        // 헤더 작성
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        // 카카오 API 호출
        return restTemplate
                .exchange(uri,
                        HttpMethod.GET,
                        httpEntity,
                        KakaoApiResponseDTO.class
                ).getBody();
    }
}
