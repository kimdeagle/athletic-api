package com.athletic.api.member.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
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
        boolean isExistMember = memberRepository.existsByNameAndMobileNo(memberRequestDto.getName(), memberRequestDto.getMobileNo());
        if (isExistMember)
            throw new CustomException(ErrorCode.EXIST_MEMBER);

        memberRepository.save(memberRequestDto.toMember());

        return ResponseDto.success(SuccessMessage.Member.ADD_MEMBER);
    }

    public ResponseDto updateMember(MemberRequestDto memberRequestDto) {
        boolean isExist = memberRepository.existsById(memberRequestDto.getId());
        if (!isExist)
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        memberRepository.save(memberRequestDto.toUpdateMember());

        return ResponseDto.success(SuccessMessage.Member.UPDATE_MEMBER);
    }

    public ResponseDto deleteMembers(List<String> idList) {
        memberRepository.deleteAllByIdInBatch(idList);

        return ResponseDto.success(SuccessMessage.getMessageByParams(SuccessMessage.Member.DELETE_MEMBERS, String.valueOf(idList.size())));
    }

    public ResponseDto uploadExcel(MultipartFile file) throws IOException {
        List<MemberRequestDto> excelList = ExcelReader.read(file, MemberRequestDto.class);

        List<String> duplicationList = getNameAndMobileNoDuplicateInExcel(excelList);
        if (!duplicationList.isEmpty())
            throw new CustomException(ErrorCode.DUPLICATION_MEMBER_IN_EXCEL, String.join("\n", duplicationList));

        List<Member> dbList = memberRepository.findAll().stream().peek(member -> member.setMobileNo(member.getMobileNo().replaceAll("[^0-9]", ""))).collect(Collectors.toList());

        List<String> existenceList = getNameListExistInDatabase(excelList, dbList);
        if (!existenceList.isEmpty())
            throw new CustomException(ErrorCode.EXIST_MEMBER_IN_DATABASE, String.join("\n", existenceList));

        memberRepository.saveAll(excelList.stream().map(MemberRequestDto::toMember).collect(Collectors.toList()));

        return ResponseDto.success(SuccessMessage.Excel.UPLOAD);
    }

    /* 엑셀 파일 내 중복 회원의 회원명 조회 */
    private List<String> getNameAndMobileNoDuplicateInExcel(List<MemberRequestDto> excelList) {
        return excelList.stream()
                .filter(member -> excelList.stream()
                        .filter(compareMember -> StringUtils.equals(member.getName(), compareMember.getName()) && StringUtils.equals(member.getMobileNo(), compareMember.getMobileNo()))
                        .count() > 1)
                .map(MemberRequestDto::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    /* 엑셀 파일 데이터 중 DB에 이미 존재하는 회원의 회원명 조회 */
    private List<String> getNameListExistInDatabase(List<MemberRequestDto> excelList, List<Member> dbList) {
        return excelList.stream()
                .filter(member -> dbList.stream()
                        .anyMatch(compareMember -> StringUtils.equals(member.getName(), compareMember.getName()) && StringUtils.equals(member.getMobileNo(), compareMember.getMobileNo())))
                .map(MemberRequestDto::getName)
                .distinct()
                .collect(Collectors.toList());
    }
}
