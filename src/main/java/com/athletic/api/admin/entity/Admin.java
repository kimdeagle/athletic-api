package com.athletic.api.admin.entity;

import com.athletic.api.common.entity.BaseEntity;
import com.athletic.api.util.converter.CryptoConverter;
import com.athletic.api.util.converter.MobileNoConverter;
import com.athletic.api.util.converter.PasswordConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminNo;

    @Column(nullable = false)
    private String adminNm;

    @Column(nullable = false)
    @Convert(converter = CryptoConverter.class)
    private String email;

    @Column(nullable = false)
    @Convert(converter = MobileNoConverter.class)
    private String mobileNo;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    private String loginPw;

    @Column(nullable = false)
    private String authNo;

    @Column(nullable = false)
    private String aprvStCd;

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

}
