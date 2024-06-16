package com.prac.music.domain.user.service;

import com.prac.music.common.service.S3Service;
import com.prac.music.domain.mail.service.MailService;
import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.LoginResponseDto;
import com.prac.music.domain.user.dto.SignoutRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.entity.UserStatusEnum;
import com.prac.music.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceSuccessTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @Mock
    private JwtService jwtService;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private UserService userService;

    SignupRequestDto requesignupRequestDto;
    LoginRequestDto loginRequestDto;
    SignoutRequestDto signoutRequestDto;
    User user;
    MultipartFile file;

    @BeforeEach
    void setUp() {
        requesignupRequestDto = SignupRequestDto.builder()
                .userId("userId")
                .password("password")
                .email("email")
                .name("name")
                .intro("intro")
                .build();

        loginRequestDto = LoginRequestDto.builder()
                .userId("userId")
                .password("password")
                .build();

        file = mock(MultipartFile.class);

        user = User.builder()
                .userId("userId")
                .password("password")
                .name("name")
                .email("email")
                .intro("intro")
                .userStatusEnum(UserStatusEnum.NORMAL)
                .profileImage("imgUrl")
                .build();
    }

    @Test
    @DisplayName("User Create Test - 성공")
    void test1() throws IOException {
        // given
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        User createdUser = userService.createUser(requesignupRequestDto, file);

        // then
        assertNotNull(createdUser);
        assertEquals("userId", createdUser.getUserId());
        assertEquals("password", createdUser.getPassword());
        assertEquals("name", createdUser.getName());
        assertEquals("email", createdUser.getEmail());
        assertEquals("intro", createdUser.getIntro());
        assertEquals(UserStatusEnum.NORMAL, createdUser.getUserStatusEnum());
        assertEquals("imgUrl", createdUser.getProfileImage());
    }


    @Test
    @DisplayName("User Login Test - 성공")
    void test2() {
        // given
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(jwtService.createToken(anyString())).thenReturn("token");
        when(jwtService.createRefreshToken(anyString())).thenReturn("refreshToken");

        // when
        LoginResponseDto responseDto = userService.loginUser(loginRequestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("token", responseDto.getToken());
        assertEquals("refreshToken", responseDto.getRefreshToken());
        assertEquals("로그인에 성공했습니다.", responseDto.getMessage());
    }

    @Test
    @DisplayName("User Logout Test")
    void test3() {
        // given
        user = User.builder().refreshToken("refreshToken").build();

        // when
        userService.logoutUser(user);

        // then
        assertNull(user.getRefreshToken());
    }

    @Test
    @DisplayName("User SignOut Test - 일반 이용자")
    void test4() {
        // given
        signoutRequestDto = SignoutRequestDto.builder().password("password").build();
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // when
        userService.signoutUser(signoutRequestDto, user);

        // then
        assertEquals(UserStatusEnum.SECESSION, user.getUserStatusEnum());
    }

}