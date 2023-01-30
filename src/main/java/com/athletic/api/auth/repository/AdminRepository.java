package com.athletic.api.auth.repository;

import com.athletic.api.auth.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByLoginId(String loginId);
    Optional<Admin> findByLoginIdAndEmail(String loginId, String email);
    boolean existsByLoginId(String loginId);
}
