package com.athletic.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNAUTHORIZED_TOKEN(901, ErrorMessage.Auth.UNAUTHORIZED_TOKEN),
    NOT_FOUND_AUTHORITY(902, ErrorMessage.Auth.NOT_FOUND_AUTHORITY),
    INVALID_ID_OR_PASSWORD(903, ErrorMessage.Auth.INVALID_ID_OR_PASSWORD),
    NOT_FOUND_ADMIN(904, ErrorMessage.Auth.NOT_FOUND_ADMIN),
    NOT_MATCH_CURRENT_PASSWORD(905, ErrorMessage.Auth.NOT_MATCH_CURRENT_PASSWORD),
    CANNOT_CHANGE_SAME_PASSWORD(906, ErrorMessage.Auth.CANNOT_CHANGE_SAME_PASSWORD),
    EXIST_LOGIN_ID(907, ErrorMessage.Auth.EXIST_LOGIN_ID),
    NOT_FOUND_AUTH_AT_SECURITY_CONTEXT(908, ErrorMessage.Auth.NOT_FOUND_AUTH_AT_SECURITY_CONTEXT),
    INVALID_APPROVE_STATUS(909, ErrorMessage.Auth.INVALID_APPROVE_STATUS),
    NOT_FOUND_MEMBER(910, ErrorMessage.Member.NOT_FOUND),
    EXIST_MEMBER(911, ErrorMessage.Member.EXIST),
    NOT_FOUND_EMAIL_TEMPLATE(912, ErrorMessage.Email.NOT_FOUND_TEMPLATE),
    FAIL_EXCEL_DOWNLOAD(913, ErrorMessage.ExcelDownload.FAIL),
    EMPTY_EXCEL_DOWNLOAD_LIST(914, ErrorMessage.ExcelDownload.EMPTY_LIST),
    OVERFLOW_MAX_ROWS_EXCEL_DOWNLOAD(915, ErrorMessage.ExcelDownload.OVERFLOW_MAX_ROWS),
    EMPTY_EXCEL_UPLOAD_FILE(916, ErrorMessage.ExcelUpload.EMPTY_FILE),
    EMPTY_EXCEL_UPLOAD_ROW(917, ErrorMessage.ExcelUpload.EMPTY_ROW),
    INVALID_EXCEL_UPLOAD_FILE(918, ErrorMessage.ExcelUpload.INVALID_FILE),
    ERROR_EXCEL_UPLOAD_BY_TEMPLATE(919, ErrorMessage.ExcelUpload.MESSAGE_TEMPLATE),
    DUPLICATION_MEMBER_IN_EXCEL(920, ErrorMessage.ExcelUpload.DUPLICATION_MEMBER_IN_EXCEL),
    EXIST_MEMBER_IN_DATABASE(921, ErrorMessage.ExcelUpload.EXIST_MEMBER_IN_DATABASE),
    FAIL_EXCEL_UPLOAD(922, ErrorMessage.ExcelUpload.FAIL),
    FAIL_SEND_EMAIL(923, ErrorMessage.Email.FAIL),
    NOT_FOUND_SAMPLE_FILE(924, ErrorMessage.File.NOT_FOUND_SAMPLE),
    NOT_FOUND_DUES(925, ErrorMessage.Dues.NOT_FOUND),

    UNKNOWN(999, ErrorMessage.UNKNOWN)
    ;

    private final int status;
    private final String message;
}
