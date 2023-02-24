package com.athletic.api.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authNo;

    @Column(nullable = false)
    private String authNm;

    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;
}
