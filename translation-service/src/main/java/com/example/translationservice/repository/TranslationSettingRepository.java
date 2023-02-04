package com.example.translationservice.repository;

import com.example.translationservice.entity.TranslationSetting;
import org.springframework.data.repository.CrudRepository;

public interface TranslationSettingRepository extends CrudRepository<TranslationSetting, Long> {

    TranslationSetting findByGroupId(Long groupId);
}
