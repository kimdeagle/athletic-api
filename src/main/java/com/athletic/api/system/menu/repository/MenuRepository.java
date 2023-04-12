package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, MenuRepositoryCustom {
}
