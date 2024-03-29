package com.athletic.api.member.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.service.MemberSelector;
import com.athletic.api.member.service.MemberService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSelector memberSelector;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseDto> getMemberList() {
        return ResponseEntity.ok(memberSelector.getMemberList());
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addMember(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.addMember(memberRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getMember(@PathVariable("id") String id) {
        return ResponseEntity.ok(memberSelector.getMember(id));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateMember(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.updateMember(memberRequestDto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteMembers(@RequestBody List<String> idList) {
        return ResponseEntity.ok(memberService.deleteMembers(idList));
    }

    @PostMapping("/excel/download")
    public void downloadExcel() { memberSelector.downloadExcel(); }

    @PostMapping("/excel/upload")
    public ResponseEntity<ResponseDto> uploadExcel(MultipartFile file) { return ResponseEntity.ok(memberService.uploadExcel(file)); }
}
