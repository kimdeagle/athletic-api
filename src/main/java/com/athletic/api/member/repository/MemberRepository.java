package com.athletic.api.member.repository;

import com.athletic.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByNameAndMobileNo(String name, String mobileNo);
}
