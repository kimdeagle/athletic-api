package com.athletic.api.member.service;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.member.dto.MemberResponseDto;
import com.athletic.api.member.repository.MemberRepository;
import com.athletic.api.util.excel.ExcelWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSelector {
    private final MemberRepository memberRepository;

    public List<MemberResponseDto> getMemberList() {
        return memberRepository.findAll(Sort.by(Sort.Order.asc("memberNm")))
                .stream().map(member -> {
                    member.setMobileNo(maskMobileNo(member.getMobileNo()));
                    return MemberResponseDto.of(member);
                }).collect(Collectors.toList());
    }

    public MemberResponseDto getMember(String memberNo) {
        return memberRepository.findById(memberNo)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /* MobileNoConverter에 의해 하이픈 적용된 상태라 마지막 substring은 9번째부터 */
    private String maskMobileNo(String mobileNo) {
        return mobileNo.substring(0, 3) + "-****-" + mobileNo.substring(9);
    }

    public void downloadExcel() {
        List<MemberResponseDto> list = getMemberList();
        ExcelWriter excelWriter = new ExcelWriter("Member List", list, MemberResponseDto.class);
        excelWriter.write();

        //TODO change ExcelWriter -> static class
        //ExcelWriter.download("회원 리스트", list, MemberResponseDto.class);
    }
}
