package com.athletic.api.authority.entity;

import com.athletic.api.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AuthorityMenuPK.class)
public class AuthorityMenu extends BaseEntity {
    @Id
    private String authNo;

    @Id
    private String menuNo;

}
