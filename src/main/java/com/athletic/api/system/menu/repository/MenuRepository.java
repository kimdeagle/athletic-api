package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, MenuRepositoryCustom {
    List<Menu> findAllByUpMenuId(String upMenuId);
}
