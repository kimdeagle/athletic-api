package com.athletic.api.system.menu.entity;

import com.athletic.api.common.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "authorities", typeClass = ListArrayType.class)
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String upMenuId;

    @Column
    private String upMenuName;

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

    @Type(type = "authorities")
    @Column
    private List<String> authorities;

    public void setUpMenuName(String upMenuName) {
        this.upMenuName = upMenuName;
    }

}
