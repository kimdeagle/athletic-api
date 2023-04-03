package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findAllByUpMenuId(String upMenuId);

    @Query(value = "select m.* from menu m where ?1 = any(m.authorities) and m.use_yn = ?2", nativeQuery = true)
    List<Menu> findByAuthNoAndUseYn(String authNo, String useYn);
}
