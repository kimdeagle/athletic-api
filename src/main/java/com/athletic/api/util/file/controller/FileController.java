package com.athletic.api.util.file.controller;

import com.athletic.api.util.file.dto.FileRequestDto;
import com.athletic.api.util.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/sample")
    public ResponseEntity<Resource> downloadSample(@RequestBody FileRequestDto fileRequestDto) {
        return fileService.downloadSample(fileRequestDto);
    }

}
