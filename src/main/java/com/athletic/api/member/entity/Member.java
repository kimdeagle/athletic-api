package com.athletic.api.member.entity;

import com.athletic.api.util.converter.CryptoConverter;
import com.athletic.api.util.converter.MobileNoConverter;
import com.athletic.api.util.converter.StringDateConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String memberNo;

    @Column(nullable = false, updatable = false)
    private String memberNm;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String email;

    @Column(nullable = false)
    @Convert(converter = MobileNoConverter.class)
    private String mobileNo;

    @Column
    @Convert(converter = StringDateConverter.class)
    private String birthday;

    @Column
    private String address;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String addressDtl;

    @Column(nullable = false)
    @Convert(converter = StringDateConverter.class)
    private String joinDt;

    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;

    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
}
