package com.prac.music.domain.mail.entity;

import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.entity.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MailTest {
    Mail mail;
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .userId("user1234")
                .email("email@email.com")
                .password("Password123!")
                .name("name")
                .intro("into Text")
                .userStatusEnum(UserStatusEnum.TEMPORARY)
                .refreshToken("refreshToken")
                .profileImage("test.jpg")
                .build();

        mail = Mail.builder()
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Mail Constructor Test")
    void test() {
        // given
        User testUser = user;

        // when
        Mail mail = Mail.builder()
                .user(testUser)
                .build();

        // then
        assertEquals(testUser, mail.getUser());
        assertEquals(testUser.getEmail(), mail.getEmail());
        assertEquals("", mail.getCode());
        assertFalse(mail.isStatus());
    }

    @Test
    @DisplayName("Mail mailAddCode Test")
    void test1() {
        // given
        String code = "code";

        // when
        mail.mailAddCode(code);

        // then
        assertEquals(code, mail.getCode());
    }
}