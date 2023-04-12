package com.athletic.api.member.repository;

public interface MemberRepositoryCustom {
    boolean existsByNameAndMobileNo(String name, String mobileNo);
}
