package com.athletic.api.member.service;

import com.athletic.api.member.entity.Member;
import com.athletic.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSelector {
    private final MemberRepository memberRepository;

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }
}
