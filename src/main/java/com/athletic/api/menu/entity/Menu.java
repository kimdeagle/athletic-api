package com.athletic.api.menu.entity;

import com.athletic.api.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuNo;

    @Column(nullable = false)
    private String menuNm;

    @Column
    private String upMenuNo;

    @Column
    private String upMenuNm;

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

    public void setUpMenuNm(String upMenuNm) {
        this.upMenuNm = upMenuNm;
    }

}
