package com.athletic.api.member.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.entity.Member;
import com.athletic.api.member.repository.MemberRepository;
import com.athletic.api.util.excel.ExcelReader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
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

    public ResponseDto deleteMember(List<String> memberNoList) {
        memberRepository.deleteAllByIdInBatch(memberNoList);
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message(memberNoList.size() + "명의 회원이 삭제되었습니다.")
                .build();
    }

    public ResponseDto uploadExcel(MultipartFile file) throws IOException {
        List<MemberRequestDto> excelList = ExcelReader.read(file, MemberRequestDto.class);

        List<String> duplicationMemberNmList = getMemberNmDuplicateInExcel(excelList);
        if (!duplicationMemberNmList.isEmpty())
            throw new CustomException(ErrorCode.DUPLICATION_MEMBER_IN_EXCEL, String.join("\n", duplicationMemberNmList));

        List<Member> dbList = memberRepository.findAll().stream().peek(member -> member.setMobileNo(member.getMobileNo().replaceAll("[^0-9]", ""))).collect(Collectors.toList());

        List<String> existenceMemberNmList = getMemberNmListExistInDatabase(excelList, dbList);
        if (!existenceMemberNmList.isEmpty())
            throw new CustomException(ErrorCode.EXIST_MEMBER_IN_DATABASE, String.join("\n", existenceMemberNmList));

        memberRepository.saveAll(excelList.stream().map(MemberRequestDto::toMember).collect(Collectors.toList()));

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("엑셀 업로드를 성공하였습니다.")
                .build();
    }

    /* 엑셀 파일 내 중복 회원의 회원명 조회 */
    private List<String> getMemberNmDuplicateInExcel(List<MemberRequestDto> excelList) {
        return excelList.stream()
                .filter(member -> excelList.stream()
                        .filter(compareMember -> StringUtils.equals(member.getMemberNm(), compareMember.getMemberNm()) && StringUtils.equals(member.getMobileNo(), compareMember.getMobileNo()))
                        .count() > 1)
                .map(MemberRequestDto::getMemberNm)
                .distinct()
                .collect(Collectors.toList());
    }

    /* 엑셀 파일 데이터 중 DB에 이미 존재하는 회원의 회원명 조회 */
    private List<String> getMemberNmListExistInDatabase(List<MemberRequestDto> excelList, List<Member> dbList) {
        return excelList.stream()
                .filter(member -> dbList.stream()
                        .anyMatch(compareMember -> StringUtils.equals(member.getMemberNm(), compareMember.getMemberNm()) && StringUtils.equals(member.getMobileNo(), compareMember.getMobileNo())))
                .map(MemberRequestDto::getMemberNm)
                .distinct()
                .collect(Collectors.toList());
    }
}
