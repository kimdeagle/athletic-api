package com.athletic.api.member.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.dto.MemberResponseDto;
import com.athletic.api.member.service.MemberSelector;
import com.athletic.api.member.service.MemberService;
import com.athletic.api.util.excel.ExcelFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSelector memberSelector;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<MemberResponseDto>> getMemberList() {
        return ResponseEntity.ok(memberSelector.getMemberList());
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> addMember(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.addMember(memberRequestDto));
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("memberNo") String memberNo) {
        return ResponseEntity.ok(memberSelector.getMember(memberNo));
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto> updateMember(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.updateMember(memberRequestDto));
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDto> deleteMember(@RequestBody List<String> memberNoList) {
        return ResponseEntity.ok(memberService.deleteMember(memberNoList));
    }

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) {
        memberSelector.downloadExcel(response);
    }
}
