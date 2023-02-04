package com.example.translationservice.service;

import com.example.translationservice.dto.TranslationSettingDto;
import com.example.translationservice.entity.LangType;
import com.example.translationservice.entity.TranslationRequestEntity;
import com.example.translationservice.entity.TranslationSetting;
import com.example.translationservice.repository.LangTypeRepository;
import com.example.translationservice.repository.TranslationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {

    @Value("${client.id}")
    private String CLIENT_ID;
    @Value("${client.secret}")
    private String CLIENT_SECRET = "PUBLIC_SECRET";
    @Value("${translation.endpoint}")
    private String ENDPOINT;

    private final RestTemplate restTemplate;
    private final TranslationSettingRepository translationSettingRepository;
    private final LangTypeRepository langTypeRepository;

    public ResponseEntity<String> translate(Long groupId, String text) {
        TranslationSetting setting = getSetting(groupId);
        String fromCode = properCode(setting.getFromLang().getCode());
        String toCode = properCode(setting.getToLang().getCode());

        TranslationRequestEntity translationRequest = TranslationRequestEntity.builder()
                .fromLang(fromCode)
                .toLang(toCode)
                .text(text)
                .build();
        return translate(translationRequest);
    }

    @Transactional
    public TranslationSetting addSetting(TranslationSettingDto translationSettingDto) {
        LangType from = getLangType(translationSettingDto.getFromLangCode());
        LangType to = getLangType(translationSettingDto.getToLangCode());

        Long id = translationSettingDto.getGroupId();
        TranslationSetting persistedSetting = translationSettingRepository.findByGroupId(id);

        if (persistedSetting != null) {
            persistedSetting.setFromLang(from);
            persistedSetting.setToLang(to);
            return persistedSetting;
        }
        TranslationSetting setting = TranslationSetting.builder()
                .groupId(translationSettingDto.getGroupId())
                .fromLang(from)
                .toLang(to)
                .build();
        return translationSettingRepository.save(setting);
    }

    public List<LangType> getLangTypes() {
        return langTypeRepository.getAll();
    }

    private LangType getLangType(String code) {
        code = properCode(code);
        LangType type = langTypeRepository.findByCode(code);
        if (type == null) {
            // todo: add exception handling
            throw new IllegalArgumentException(
                    String.format("LangType with code `%s` doesn't exist", code)
            );
        }
        return type;
    }
    private String properCode(String code) {
        if ("zh".equals(code)) {
            code = "zh-CN";
        }
        else if ("ce".equals(code)) {
            code = "ceb";
        }
        else if ("hm".equals(code)) {
            code = "hmn";
        }
        return code;
    }

    private TranslationSetting getSetting(Long groupId) {
        TranslationSetting setting = translationSettingRepository.findByGroupId(groupId);
        if (setting == null) {
            throw new IllegalArgumentException(
                    String.format("Setting for `%s` group doesn't exist", groupId)
            );
        }
        return setting;
    }

    private ResponseEntity<String> translate(TranslationRequestEntity requestEntity) {
        // set headers for the endpoint request
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-WM-CLIENT-ID", CLIENT_ID);
        headers.add("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        // create http entity for the endpoint request
        HttpEntity<TranslationRequestEntity> entity = new HttpEntity<>(
                requestEntity, headers
        );
        return restTemplate.postForEntity(ENDPOINT, entity, String.class);
    }
}
