package com.athletic.api.member.service;

import com.athletic.api.auth.config.SecurityUtil;
import com.athletic.api.auth.util.CryptUtil;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.entity.Member;
import com.athletic.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CryptUtil cryptUtil;

    public ResponseDto addMember(MemberRequestDto memberRequestDto) {
        memberRequestDto.setMobileNo(cryptUtil.encryptAES256(memberRequestDto.getMobileNo()));
        boolean isExistMember = memberRepository.existsByMemberNmAndMobileNo(memberRequestDto.getMemberNm(), memberRequestDto.getMobileNo());
        if (isExistMember) throw new CustomException(ErrorCode.EXIST_MEMBER);
//        if (isExistMember) {
//            return ResponseDto.builder()
//                    .code(ResponseDto.FAIL)
//                    .message("이미 존재하는 회원입니다.\n회원명 또는 휴대폰 번호를 확인해주세요.")
//                    .build();
//        }
        memberRequestDto.setEmail(cryptUtil.encryptAES256(memberRequestDto.getEmail()));
        memberRequestDto.setAddressDtl(cryptUtil.encryptAES256(memberRequestDto.getAddressDtl()));
        String adminNo = SecurityUtil.getCurrentAdminNo();
        memberRequestDto.setRegId(adminNo);
        memberRequestDto.setRegDt(new Date());
        memberRequestDto.setModId(adminNo);
        memberRequestDto.setModDt(new Date());
        memberRepository.save(memberRequestDto.toMember());
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회원 추가가 정상적으로 완료되었습니다.")
                .build();
    }

    public Member updateMember(MemberRequestDto memberRequestDto) {
        return memberRepository.findById("1").orElseThrow(() -> new RuntimeException("error test"));
    }
}
