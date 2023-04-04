package com.athletic.api.util.code;

import org.json.simple.JSONArray;
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
    public JSONArray getCodeListByGroupCode(@PathVariable("groupCode") String groupCode) {
        return CodeFinder.findByGroupCode(groupCode);
    }

    @GetMapping
    public JSONArray getCodeListByGroupCodes(@RequestParam List<String> groupCodes) {
        return CodeFinder.findByGroupCodes(groupCodes);
    }
}
