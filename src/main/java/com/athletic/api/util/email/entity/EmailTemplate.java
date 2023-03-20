package com.athletic.api.util.email.entity;

import com.athletic.api.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate extends BaseEntity {
    @Id
    private String templateCd;

    @Column(nullable = false)
    private String templateNm;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

}
