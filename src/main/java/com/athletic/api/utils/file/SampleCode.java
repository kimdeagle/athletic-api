package com.athletic.api.utils.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SampleCode {
    MEMBER("/excel/member", "memberUploadSample.xlsx"),
    DUES("/excel/dues", "duesUploadSample.xlsx"),
    SCHEDULE("/excel/schedule", "scheduleUploadSample.xlsx"),
    ;

    private String path;
    private String filename;
}
