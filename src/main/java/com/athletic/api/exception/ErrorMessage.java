package com.athletic.api.exception;

public class ErrorMessage {
    /* Auth */

    /* Email */
    public static class Email {
        public static final String FAIL = "이메일 전송을 실패했습니다.";
    }

    /* Excel Upload */
    public static class ExcelUpload {
        public static final String MESSAGE_TEMPLATE = "[{0}셀 오류 발생]\n{1}";
        public static final String INVALID_FILE = "유효하지 않은 엑셀 파일입니다.";
        public static final String EMPTY_FILE = "업로드 할 파일이 없습니다.";
        public static final String EMPTY_ROW = "{0}행에 데이터가 없습니다.\n확인 후 다시 업로드 해주시기 바랍니다.";
        public static final String EMPTY_REQUIRED = "필수 항목을 입력하세요.";
        public static final String INVALID_EMAIL = "올바른 이메일을 입력하세요.";
        public static final String INVALID_MOBILE_NO = "올바른 휴대폰 번호를 입력하세요.";
        public static final String INVALID_BIRTHDAY = "올바른 생년월일을 입력하세요.";
        public static final String INVALID_JOIN_DT = "올바른 입회일자를 입력하세요.";
        public static final String INVALID_DATA_FORMAT = "데이터 형식이 올바르지 않습니다.";
        public static final String DUPLICATION_MEMBER_IN_EXCEL = "엑셀 파일 내 중복된 회원이 있습니다.\n아래 회원명 확인 후 다시 업로드 해주시기 바랍니다.\n\n[회원명]\n{0}";
        public static final String EXIST_MEMBER_IN_DATABASE = "이미 존재하는 회원이 있습니다.\n아래 회원명 확인 후 다시 업로드 해주시기 바랍니다.\n\n[회원명]\n{0}";
        public static final String FAIL = "엑셀 업로드를 실패했습니다.";
    }

    /* Excel Download */
    public static class ExcelDownload {
        public static final String FAIL = "엑셀 다운로드를 실패했습니다.";
        public static final String OVERFLOW_MAX_ROWS = "{0}개 행을 초과하여 다운로드 할 수 없습니다.";
    }

    public static final String UNKNOWN = "알 수 없는 오류가 발생했습니다.\n관리자에게 문의 바랍니다.";

}
