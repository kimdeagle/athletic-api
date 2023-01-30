package com.athletic.api.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminNo;
    @Column(nullable = false)
    private String adminNm;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String mobileNo;
    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String loginPw;
    @Column(nullable = false)
    private String authNo;
    @Column(nullable = false)
    private String aprvStCd;
    @Column(nullable = false)
    private String regId;
    @Column(nullable = false)
    private Date regDt;
    @Column(nullable = false)
    private String modId;
    @Column(nullable = false)
    private Date modDt;

    public Admin(String adminNo, String adminNm, String email, String mobileNo, String loginId, String loginPw, String authNo, String aprvStCd, String regId, Date regDt, String modId, Date modDt) {
        this.adminNo = adminNo;
        this.adminNm = adminNm;
        this.email = email;
        this.mobileNo = mobileNo;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.authNo = authNo;
        this.aprvStCd = aprvStCd;
        this.regId = regId;
        this.regDt = regDt;
        this.modId = modId;
        this.modDt = modDt;
    }

}
