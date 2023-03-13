package com.athletic.api.admin.entity;

import com.athletic.api.util.converter.CryptoConverter;
import com.athletic.api.util.converter.MobileNoConverter;
import com.athletic.api.util.converter.PasswordConverter;
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
public class Admin {
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

    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public void setModDt(LocalDateTime modDt) {
        this.modDt = modDt;
    }
}
