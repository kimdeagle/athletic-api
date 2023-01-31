package com.athletic.api.menu.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuNo;
    @Column(nullable = false)
    private String menuNm;
    @Column
    private String upMenuNo;
    @Column
    private String menuUrl;
    @Column
    private String iconNm;
    @Column
    private Long menuLevel;
    @Column(nullable = false)
    private Long sortSeq;
    @Column(nullable = false)
    private String useYn;
    @Column(nullable = false)
    private String regId;
    @Column(nullable = false)
    private Date regDt;
    @Column(nullable = false)
    private String modId;
    @Column(nullable = false)
    private Date modDt;
}
