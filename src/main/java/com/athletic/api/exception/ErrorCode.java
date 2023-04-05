package com.athletic.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNAUTHORIZED_TOKEN(901, "권한 정보가 없는 토큰입니다."),
    AUTH_NOT_FOUND(902, "권한 정보가 없습니다."),
    INVALID_ID_OR_PASSWORD(903, "아이디 또는 비밀번호를 다시 확인해주세요."),
    ADMIN_NOT_FOUND(904, "계정 정보를 찾을 수 없습니다."),
    NOT_MATCH_CURRENT_PASSWORD(905, "현재 비밀번호가 일치하지 않습니다."),
    CANNOT_CHANGE_SAME_PASSWORD(906, "기존과 동일한 비밀번호로 변경할 수 없습니다."),
    EXIST_LOGIN_ID(907, "이미 존재하는 아이디입니다."),
    SECURITY_CONTEXT_NOT_FOUND(908, "Security Context에 인증 정보가 없습니다."),
    INVALID_APPROVE_STATUS(909, ErrorMessage.Auth.INVALID_APPROVE_STATUS),
    MEMBER_NOT_FOUND(910, "존재하지 않는 회원입니다."),
    EXIST_MEMBER(911, "이미 존재하는 회원입니다.\n회원명 또는 휴대폰 번호를 확인하세요."),
    EMAIL_TEMPLATE_NOT_FOUND(912, "이메일 템플릿을 찾을 수 없습니다."),
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

    UNKNOWN(999, ErrorMessage.UNKNOWN)
    ;

    private final int status;
    private final String message;
}
