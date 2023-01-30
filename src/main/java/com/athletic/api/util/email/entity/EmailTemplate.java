package com.athletic.api.util.email.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class EmailTemplate {
    @Id
    private String templateCd;
    @Column
    private String templateNm;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String regId;
    @Column
    private Date regDt;
    @Column
    private String modId;
    @Column
    private Date modDt;
}
