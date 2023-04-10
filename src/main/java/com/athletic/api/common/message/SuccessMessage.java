package com.athletic.api.common.message;

public class SuccessMessage {

    public static String getMessageByParams(String message, String... params) {
        for (int i=0; i<params.length; i++) {
            message = message.replace("{" + i + "}", params[i]);
        }
        return message;
    }

    public static class Admin {
        public static final String CHANGE_PASSWORD = "비밀번호가 변경되었습니다.";
        public static final String DELETE_ADMIN = "계정이 삭제되었습니다.";
    }

    public static class Auth {
        public static final String REQUEST_JOIN = "{0}님. 계정생성 요청이 완료되었습니다.";
        public static final String RESET_PASSWORD = "임시 비밀번호를 이메일로 전송했습니다.\n확인 후 로그인하세요.";
        public static final String LOGOUT = "로그아웃 되었습니다.";
    }

    public static class Dues {
        public static final String DELETE_DUES = "회비 내역이 삭제되었습니다.";
        public static final String ADD_DUES = "회비 내역이 추가되었습니다.";
        public static final String UPDATE_DUES = "회비 내역이 수정되었습니다.";
    }

    public static class Member {
        public static final String ADD_MEMBER = "회원이 추가되었습니다.";
        public static final String UPDATE_MEMBER = "회원이 수정되었습니다.";
        public static final String DELETE_MEMBERS = "{0}명의 회원이 삭제되었습니다.";
    }

    public static class Excel {
        public static final String UPLOAD = "엑셀 업로드가 완료되었습니다.";
    }

    public static class Menu {
        public static final String ADD_MENU = "메뉴가 추가되었습니다.";
        public static final String UPDATE_MENU = "메뉴가 수정되었습니다.";
        public static final String DELETE_MENU = "메뉴가 삭제되었습니다.";
    }

    public static class Schedule {
        public static final String ADD_SCHEDULE = "일정이 추가되었습니다.";
        public static final String UPDATE_SCHEDULE = "일정이 수정되었습니다.";
        public static final String DELETE_SCHEDULE = "일정이 삭제되었습니다.";
    }
}
