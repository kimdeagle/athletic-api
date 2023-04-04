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

    @GetMapping("/{groupId}")
    public JSONArray getCodeListByGroupId(@PathVariable("groupId") String groupId) {
        return CodeFinder.findByGroupId(groupId);
    }

    @GetMapping
    public JSONArray getCodeListByGroupIds(@RequestParam List<String> groupIds) {
        return CodeFinder.findByGroupIds(groupIds);
    }
}
