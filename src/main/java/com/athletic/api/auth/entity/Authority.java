package com.athletic.api.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authNo;
    @Column(nullable = false)
    private String authNm;
    @Column(nullable = false)
    private String regId;
    @Column(nullable = false)
    private Date regDt;
    @Column(nullable = false)
    private String modId;
    @Column(nullable = false)
    private Date modDt;

    public Authority(String authNo, String authNm, String regId, Date regDt, String modId, Date modDt) {
        this.authNo = authNo;
        this.authNm = authNm;
        this.regId = regId;
        this.regDt = regDt;
        this.modId = modId;
        this.modDt = modDt;
    }
}
