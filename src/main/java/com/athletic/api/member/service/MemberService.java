package com.athletic.api.member.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseDto addMember(MemberRequestDto memberRequestDto) {
        boolean isExistMember = memberRepository.existsByMemberNmAndMobileNo(memberRequestDto.getMemberNm(), memberRequestDto.getMobileNo());
        if (isExistMember)
            throw new CustomException(ErrorCode.EXIST_MEMBER);
        memberRepository.save(memberRequestDto.toMember());
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회원이 추가되었습니다.")
                .build();
    }

    public ResponseDto updateMember(MemberRequestDto memberRequestDto) {
        boolean isExist = memberRepository.existsById(memberRequestDto.getMemberNo());
        if (!isExist)
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        memberRepository.save(memberRequestDto.toUpdateMember());
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회원이 수정되었습니다.")
                .build();
    }
}
