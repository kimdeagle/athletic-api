package com.athletic.api.authority.repository;

import com.athletic.api.authority.entity.AuthorityMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityMenuRepository extends JpaRepository<AuthorityMenu, String> {
    void deleteAllByMenuNo(String menuNo);

    List<AuthorityMenu> findAllByAuthNo(String currentAdminAuthNo);
}
