package com.my.pharmacy.service;

import com.my.pharmacy.dto.DocumentDTO;
import com.my.pharmacy.dto.KakaoApiResponseDTO;
import com.my.pharmacy.dto.OutputDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class KakaoCategorySearchService {
    private final RestTemplate restTemplate;
    private static final String CATEGORY = "PM9";

    // Uri 만들기
    private static final String KAKAO_CATEGORY_URL =
            "https://dapi.kakao.com/v2/local/search/category.json";

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoCategorySearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public KakaoApiResponseDTO resultCategorySearch
            (double latitude, double longitude, int radius) {
        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromHttpUrl(
                        KAKAO_CATEGORY_URL
                );
        uriBuilder.queryParam("category_group_code", CATEGORY);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", radius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();
        // 헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET,
                        httpEntity, KakaoApiResponseDTO.class)
                .getBody();
    }

    public List<OutputDTO> makeOutputDto(
            List<DocumentDTO> documentList) {
        // 리스트에다 3개만 담기
        return documentList.stream()
                .map(x -> convertToOutputDto(x))
                .limit(3)
                .toList();
    }

    private OutputDTO convertToOutputDto(DocumentDTO document) {
        // 로드뷰 URL
        // https://map.kakao.com/link/roadview/
        // 37.5723485639444,126.98741227909
        String ROAD_VIEW_URL = "https://map.kakao.com/link/roadview/";

        // 지도 URL
        // https://map.kakao.com/link/map/CU 낙원점,
        // 37.5723485639444,126.98741227909
        String DIRECTION_URL = "https://map.kakao.com/link/map/";
        String params = String.join(",", document.getPlaceName(),
                String.valueOf(document.getLatitude()) ,
                String.valueOf(document.getLongitude()));
        String mapUrl = UriComponentsBuilder
                .fromHttpUrl(DIRECTION_URL + params)
                .toUriString();

        String roadUrl = ROAD_VIEW_URL + document.getLatitude() +
                "," + document.getLongitude();
        return OutputDTO.builder()
                .pharmacyName(document.getPlaceName())
                .pharmacyAddress(document.getAddressName())
                .directionURL(mapUrl)
                .roadViewURL(roadUrl)
                .distance(String.format("%.0f m", document.getDistance()))
                .build();
    }
}
