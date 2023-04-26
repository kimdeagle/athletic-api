package com.athletic.api.admin.repository;

import com.athletic.api.admin.dto.AdminResponseDto;
import com.athletic.api.admin.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepositoryCustom {
    boolean existsByLoginId(String loginId);
    Optional<Admin> findByLoginId(String loginId);
    Optional<Admin> findByLoginIdAndEmail(String loginId, String email);
    List<String> findAllNameByAuthorityId(String id);
}
