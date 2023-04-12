package com.athletic.api.admin.repository;

import com.athletic.api.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>, AdminRepositoryCustom {
}
