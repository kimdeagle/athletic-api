package com.athletic.api.authority.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class AuthorityMenuPK implements Serializable {
    private String authNo;
    private String menuNo;
}
