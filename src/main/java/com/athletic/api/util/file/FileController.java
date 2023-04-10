package com.athletic.api.util.file;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final String SAMPLE_PATH_PREFIX = "/sample";
    private final String STATIC_PATH = "classpath:static/files";
    private final ResourceLoader resourceLoader;

    @PostMapping("/sample")
    public ResponseEntity<Resource> downloadSample(@RequestBody FileRequestDto fileRequestDto) {
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
