package com.prac.music.domain.user.entity;

public enum UserStatusEnum {
    TEMPORARY(Authority.TEMPORARY),  // 이메일 인증 전
    NORMAL(Authority.NORMAL),  // 이메일 인증 완료
    SECESSION(Authority.SECESSION);  // 회원 탈퇴

    private final String authority;

    UserStatusEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String TEMPORARY = "TEMPORARY";
        public static final String NORMAL = "NORMAL";
        public static final String SECESSION = "SECESSION";
    }
}