package com.example.translationservice.repository;

import com.example.translationservice.entity.LangType;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface LangTypeRepository extends CrudRepository<LangType, Long> {

    LangType findByCode(String code);

    default List<LangType> getAll() {
        List<LangType> langs = new ArrayList<>();
        findAll().iterator().forEachRemaining(langs::add);
        return langs;
    }
}
