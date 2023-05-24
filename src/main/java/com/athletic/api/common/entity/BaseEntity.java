package com.athletic.api.common.entity;

import com.athletic.api.security.utils.SecurityUtils;
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
public class BaseEntity {
    @Column(updatable = false)
    private String regId;

    @Column(updatable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String modId;

    @Column(nullable = false)
    private LocalDateTime modDt;

    public void setModColumnsDefaultValue() {
        this.modId = SecurityUtils.getCurrentId();
        this.modDt = LocalDateTime.now();
    }
}
