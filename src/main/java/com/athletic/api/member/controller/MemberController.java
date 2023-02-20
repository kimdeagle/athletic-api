package com.athletic.api.member.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.member.dto.MemberRequestDto;
import com.athletic.api.member.entity.Member;
import com.athletic.api.member.service.MemberSelector;
import com.athletic.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSelector memberSelector;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok(memberSelector.getMemberList());
    }

    @PostMapping("")
    public ResponseDto addMember(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.addMember(memberRequestDto);
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<Member> getMember(@PathVariable("memberNo") String memberNo) {
        return ResponseEntity.ok(memberSelector.getMember(memberNo));
    }

    @PutMapping("")
    public Member updateMember(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.updateMember(memberRequestDto);
    }
}
