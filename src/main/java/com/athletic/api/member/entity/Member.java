package com.athletic.api.member.entity;

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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String memberNo;
    @Column(nullable = false)
    private String memberNm;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String mobileNo;
    @Column
    private String birthday;
    @Column
    private String address;
    @Column
    private String addressDtl;
    @Column(nullable = false)
    private String joinDt;
    @Column(nullable = false)
    private String regId;
    @Column(nullable = false)
    private Date regDt;
    @Column(nullable = false)
    private String modId;
    @Column(nullable = false)
    private Date modDt;

    public Member(String memberNo, String memberNm, String email, String mobileNo, String birthday, String address, String addressDtl, String joinDt, String regId, Date regDt, String modId, Date modDt) {
        this.memberNo = memberNo;
        this.memberNm = memberNm;
        this.email = email;
        this.mobileNo = mobileNo;
        this.birthday = birthday;
        this.address = address;
        this.addressDtl = addressDtl;
        this.joinDt = joinDt;
        this.regId = regId;
        this.regDt = regDt;
        this.modId = modId;
        this.modDt = modDt;
    }
}
