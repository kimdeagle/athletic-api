package com.athletic.api.common.entity;

import com.athletic.api.auth.util.SecurityUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;

    public void setModColumnsDefaultValue() {
        this.modId = SecurityUtil.getCurrentAdminNo();
        this.modDt = LocalDateTime.now();
    }
}
