package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTest {
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
    }

    @Test
    @DisplayName("User Constructor Test")
    void test() {
        // given
        String userid = "userId";
        String email = "email@email.com";
        String password = "Password123!";
        String name = "name";
        String intro = "into Text";
        String refreshToken = "refreshToken";
        String profileImage = "profileImage.jpg";

        // when
        User user = User.builder()
                .id(1L)
                .userId(userid)
                .email(email)
                .password(password)
                .name(name)
                .intro(intro)
                .userStatusEnum(UserStatusEnum.TEMPORARY)
                .refreshToken(refreshToken)
                .profileImage(profileImage)
                .build();

        // then
        assertEquals(1L, user.getId());
        assertEquals(userid, user.getUserId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(intro, user.getIntro());
        assertEquals(UserStatusEnum.TEMPORARY, user.getUserStatusEnum());
        assertEquals(refreshToken, user.getRefreshToken());
        assertEquals(profileImage, user.getProfileImage());
    }

    @Test
    @DisplayName("isExist Test")
    void test1() {
        // given

        // when
        boolean exist = user.isExist();

        // then
        assertTrue(exist);
    }

    @Test
    @DisplayName("nonPasswordProfileUpdate Test")
    void test2() {
        // given
        ProfileRequestDto profileRequestDto = mock(ProfileRequestDto.class);
        when(profileRequestDto.getName()).thenReturn("update User");
        when(profileRequestDto.getEmail()).thenReturn("updateEmail@email.com");
        when(profileRequestDto.getIntro()).thenReturn("update intro");

        // when
        user.nonPasswordProfileUpdate(profileRequestDto, "updateImage.jpg");

        // then
        assertEquals("update User", user.getName());
        assertEquals("updateEmail@email.com", user.getEmail());
        assertEquals("update intro", user.getIntro());
        assertEquals("updateImage.jpg", user.getProfileImage());
    }

    @Test
    @DisplayName("inPasswordProfileUpdate Test")
    void test3() {
        // given
        ProfileRequestDto profileRequestDto = mock(ProfileRequestDto.class);
        when(profileRequestDto.getName()).thenReturn("update User");
        when(profileRequestDto.getEmail()).thenReturn("updateEmail@email.com");
        when(profileRequestDto.getIntro()).thenReturn("update intro");
        when(profileRequestDto.getPassword()).thenReturn("Password123!");
        when(profileRequestDto.getNewPassword()).thenReturn("newPassword123!");

        // when
        user.inPasswordProfileUpdate(profileRequestDto, "newPassword123!","updateImage.jpg");

        // then
        assertEquals("update User", user.getName());
        assertEquals("updateEmail@email.com", user.getEmail());
        assertEquals("update intro", user.getIntro());
        assertEquals("updateImage.jpg", user.getProfileImage());
        assertEquals("newPassword123!", user.getPassword());
    }

    @Test
    @DisplayName("updateRefresh Test")
    void test4() {
        // given
        String newRefreshToken = "newRefreshToken";

        // when
        user.updateRefresh(newRefreshToken);

        // then
        assertEquals("newRefreshToken", user.getRefreshToken());
    }

    @Test
    @DisplayName("updateStatusVeryfied Test")
    void test5() {
        // given

        // when
        user.updateStatusVeryfied();

        // then
        assertEquals(UserStatusEnum.NORMAL, user.getUserStatusEnum());
    }

    @Test
    @DisplayName("updateStatusSignout Test")
    void test6() {
        // given

        // when
        user.updateStatusSignout();

        // then
        assertEquals(UserStatusEnum.SECESSION, user.getUserStatusEnum());
    }
}