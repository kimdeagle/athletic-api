package com.athletic.api.system.admin.repository;

import com.athletic.api.system.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>, AdminRepositoryCustom {
}
