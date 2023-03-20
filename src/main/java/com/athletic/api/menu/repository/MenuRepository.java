package com.athletic.api.menu.repository;

import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findAllByUpMenuNo(String menuNo);
    List<Menu> findByMenuNoInAndUseYn(List<String> menuNoList, String useYn);
}
