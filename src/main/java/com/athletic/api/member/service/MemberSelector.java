package com.athletic.api.member.service;

import com.athletic.api.auth.util.CryptUtil;
import com.athletic.api.member.entity.Member;
import com.athletic.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSelector {
    private final MemberRepository memberRepository;
    private final CryptUtil cryptUtil;

    public List<Member> getMemberList() {
        List<Member> memberList = memberRepository.findAll();
        memberList.forEach(member -> {
            decryptMemberColumns(member);
            member.setMobileNo(maskMobileNo(member.getMobileNo()));
        });
        return memberList;
    }

    public Member getMember(String memberNo) {
        Member member = memberRepository.findById(memberNo).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        decryptMemberColumns(member);
        return member;
    }

    private void decryptMemberColumns(Member member) {
        member.setEmail(cryptUtil.decryptAES256(member.getEmail()));
        member.setMobileNo(cryptUtil.decryptAES256(member.getMobileNo()));
        member.setAddressDtl(cryptUtil.decryptAES256(member.getAddressDtl()));
    }

    private String maskMobileNo(String mobileNo) {
        return mobileNo.substring(0, 3) + "-****-" + mobileNo.substring(7);
    }
}
