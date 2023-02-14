package com.athletic.api.member.controller;

import com.athletic.api.member.entity.Member;
import com.athletic.api.member.service.MemberSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSelector memberSelector;

    @GetMapping("")
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok(memberSelector.getMemberList());
    }
}
