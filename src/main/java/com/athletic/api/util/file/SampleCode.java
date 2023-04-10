package com.athletic.api.util.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SampleCode {
    MEMBER("/excel/member", "memberUploadSample.xlsx"),
    DUES("/excel/dues", "duesUploadSample.xlsx"),
    ;

    private String path;
    private String filename;
}
