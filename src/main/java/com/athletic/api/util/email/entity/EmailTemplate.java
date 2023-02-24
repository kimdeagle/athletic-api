package com.athletic.api.util.email.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    @Id
    private String templateCd;

    @Column(nullable = false)
    private String templateNm;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;
}
