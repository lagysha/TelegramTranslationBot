package com.example.translationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@Table(name="translation_settings")
@Builder
@AllArgsConstructor
public class TranslationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "group_id", unique = true)
    private Long groupId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_lang_id")
    private LangType fromLang;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_lang_id")
    private LangType toLang;

    public TranslationSetting() {
    }
}
