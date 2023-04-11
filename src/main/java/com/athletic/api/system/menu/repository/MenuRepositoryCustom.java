package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;

import java.util.List;

public interface MenuRepositoryCustom {
    List<Menu> findAllByAuthorityIdAndUseYn();
}
