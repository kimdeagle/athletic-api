package com.athletic.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    WAIT_ADMIN_STATUS(909, "가입대기 상태의 계정입니다."),
    MEMBER_NOT_FOUND(910, "존재하지 않는 회원입니다."),
    EXIST_MEMBER(911, "이미 존재하는 회원입니다.\n회원명 또는 휴대폰 번호를 확인해주세요."),
    EMAIL_TEMPLATE_NOT_FOUND(912, "이메일 템플릿을 찾을 수 없습니다."),
    EMPTY_DOWNLOAD_MEMBER(913, "다운로드 할 회원이 없습니다."),
    FAIL_DOWNLOAD_EXCEL(914, "엑셀 다운로드를 실패했습니다."),
    OVERFLOW_MAX_ROWS_DOWNLOAD_EXCEL(915, "현재 지원하는 엑셀 파일의 최대 행을 초과할 수 없습니다."),
    NOT_SUPPORT_EXCEL_TYPE(916, "지원하지 않는 타입입니다."),
    WRONG_RGB(917, "RGB 값이 잘못되었습니다."),
    FAIL_INIT_BORDER(918, "fail initialized border"),
    NO_SEARCH_FIELD(919, "필드를 찾을 수 없습니다."),
    INVALID_CELL_STYLE(920, "Cell 스타일이 올바르지 않습니다."),
    NPE_ENUM_NAME(921, "enumName must not be null!!"),
    INVALID_ENUM_NAME(922, "유효하지 않은 enumName 입니다.")
    ;

    private final int status;
    private final String message;
}
