package com.my.pharmacy.controller;

import com.my.pharmacy.dto.DocumentDTO;
import com.my.pharmacy.dto.InputDTO;
import com.my.pharmacy.dto.KakaoApiResponseDTO;
import com.my.pharmacy.dto.OutputDTO;
import com.my.pharmacy.service.KakaoAddressSearchService;
import com.my.pharmacy.service.KakaoCategorySearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FormController {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    public FormController(KakaoAddressSearchService
                                  kakaoAddressSearchService,
                          KakaoCategorySearchService
                                  kakaoCategorySearchService) {
        this.kakaoAddressSearchService =
                        kakaoAddressSearchService;
        this.kakaoCategorySearchService = kakaoCategorySearchService;
    }

    @GetMapping("/")
    public String mainForm() {
        return "main";
    }
    @GetMapping("/output")
    public String outputForm() {
        return "output";
    }

    @PostMapping("/search")
    public String searchAddress(InputDTO dto
                                , Model model) {
        KakaoApiResponseDTO kakaoApiResponseDTO =
                kakaoAddressSearchService.requestAddressSearch(
                        dto.getAddress()
                );
        // Documents 만 빼서 dto 만들기
        DocumentDTO documentDTO = kakaoApiResponseDTO
                .getDocumentList().get(0);


        // 카테고리 검색
        int radius = 10000;

        KakaoApiResponseDTO recommendationDto =
            kakaoCategorySearchService.resultCategorySearch(
                  documentDTO.getLatitude(),
                    documentDTO.getLongitude(),
                    radius
            );
        List<OutputDTO> outputDTOList =
                kakaoCategorySearchService.makeOutputDto(
                        recommendationDto.getDocumentList()
                );
        System.out.println(outputDTOList);

        model.addAttribute("outputList", outputDTOList);
        return "output";
    }
}
