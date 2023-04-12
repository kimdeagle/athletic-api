package com.athletic.api.util.file.service;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.file.dto.FileRequestDto;
import com.athletic.api.util.file.SampleCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileService {

    private final String SAMPLE_PATH_PREFIX = "/sample";
    private final String STATIC_PATH = "classpath:static/files";
    private final ResourceLoader resourceLoader;

    public ResponseEntity<Resource> downloadSample(FileRequestDto fileRequestDto) {
        SampleCode sampleCode = SampleCode.valueOf(fileRequestDto.getSampleName());
        String path = sampleCode.getPath();
        String filename = sampleCode.getFilename();

        Resource resource = resourceLoader.getResource(STATIC_PATH + SAMPLE_PATH_PREFIX + path + "/" + filename);

        if (!resource.exists())
            throw new CustomException(ErrorCode.NOT_FOUND_SAMPLE_FILE);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8).build().toString())
                .body(resource);
    }
}
