package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;

import java.util.List;

public interface MenuRepositoryCustom {
    List<Menu> findAllByUpMenuId(String upMenuId);
    List<Menu> findAllByAdminIdAndUseYn(String adminId, String useYn);
    List<String> findAllNameByAuthorityId(String id);
}
