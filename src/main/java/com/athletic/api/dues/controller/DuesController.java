package com.athletic.api.dues.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.dues.dto.DuesRequestDto;
import com.athletic.api.dues.service.DuesSelector;
import com.athletic.api.dues.service.DuesService;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
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

import java.io.IOException;


@RestController
@RequestMapping("/dues")
@RequiredArgsConstructor
public class DuesController {
    private final DuesSelector duesSelector;
    private final DuesService duesService;

    @GetMapping
    public ResponseEntity<ResponseDto> getDuesList() {
        return ResponseEntity.ok(duesSelector.getDuesList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getDues(@PathVariable("id") String id) {
        return ResponseEntity.ok(duesSelector.getDues(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteDues(@PathVariable("id") String id) {
        return ResponseEntity.ok(duesService.deleteDues(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addDues(@RequestBody DuesRequestDto duesRequestDto) {
        return ResponseEntity.ok(duesService.addDues(duesRequestDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateDues(@RequestBody DuesRequestDto duesRequestDto) {
        return ResponseEntity.ok(duesService.updateDues(duesRequestDto));
    }

    @GetMapping("/amount/this-month")
    public ResponseEntity<ResponseDto> getAmountThisMonth() {
        return ResponseEntity.ok(duesSelector.getAmountThisMonth());
    }

    @PostMapping("/excel/download")
    public void downloadExcel(@RequestBody ExcelDownloadSearchCondition condition) {
        duesSelector.downloadExcel(condition);
    }

    @PostMapping("/excel/upload")
    public ResponseEntity<ResponseDto> uploadExcel(MultipartFile file) { return ResponseEntity.ok(duesService.uploadExcel(file)); }

}
