package com.athletic.api.system.authority.repository;

import com.athletic.api.system.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>, AuthorityRepositoryCustom {
}
