package com.athletic.api.member.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.member.dto.MemberResponseDto;
import com.athletic.api.member.repository.MemberRepository;
import com.athletic.api.utils.excel.ExcelWriter;
import com.athletic.api.utils.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSelector {
    private final MemberRepository memberRepository;

    public ResponseDto getMemberList() {
        List<MemberResponseDto> list = getMemberResponseDtoList();

        return ResponseDto.success(list);
    }

    private List<MemberResponseDto> getMemberResponseDtoList() {
        return memberRepository.findAll(Sort.by(Sort.Order.asc("name")))
                .stream()
                .map(MemberResponseDto::of)
                .peek(member -> {
                    member.setMobileNo(Util.maskMobileNo(member.getMobileNo()));
                    member.setEmail(Util.maskEmail(member.getEmail()));
                })
                .collect(Collectors.toList());
    }

    public ResponseDto getMember(String id) {
        MemberResponseDto dto = memberRepository.findById(id)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        return ResponseDto.success(dto);
    }

    public void downloadExcel() {
        List<MemberResponseDto> list = getMemberResponseDtoList();

        ExcelWriter.write("회원 리스트", list, MemberResponseDto.class);
    }
}
