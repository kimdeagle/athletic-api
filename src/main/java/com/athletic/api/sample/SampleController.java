package com.athletic.api.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping
    public String getSample(HttpServletRequest req, Sample sample) {
        System.out.println("sample : " + sample);
        return "GET Sample";
    }

    @PostMapping
    public String postSample(@RequestBody Sample sample2) {
        System.out.println("sample2 : " + sample2);
        return "POST Sample";
    }
}
