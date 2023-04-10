package com.athletic.api.util.code;

import com.athletic.api.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @GetMapping("/{groupCode}")
    public ResponseDto getCodeListByGroupCode(@PathVariable("groupCode") String groupCode) {
        return CodeFinder.findByGroupCode(groupCode);
    }

    @GetMapping
    public ResponseDto getCodeListByGroupCodes(@RequestParam List<String> groupCodes) {
        return CodeFinder.findByGroupCodes(groupCodes);
    }
}
