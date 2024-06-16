package com.prac.music.domain.user.service;

import com.prac.music.common.exception.PasswordRuntimeException;
import com.prac.music.common.exception.UserServiceException;
import com.prac.music.common.service.S3Service;
import com.prac.music.domain.mail.service.MailService;
import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.SignoutRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.entity.UserStatusEnum;
import com.prac.music.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceFailureTest {
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
        MockitoAnnotations.openMocks(this);

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
    @DisplayName("User Create Test - 실패")
    void test1() {
        // given
        User user = mock(User.class);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, ()
                -> userService.createUser(requesignupRequestDto, file)
        );

        // then
        assertEquals("이미 중복된 사용자가 존재합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("User Login Test - 실패")
    void test2() {
        // given
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, ()
                -> userService.loginUser(loginRequestDto)
        );

        // then
        assertEquals("아이디를 다시 확인해주세요", exception.getMessage());
    }


    @Test
    @DisplayName("User SignOut Test - 탈퇴 이용자")
    void test3() {
        // given
        signoutRequestDto = SignoutRequestDto.builder().password("password").build();
        user = User.builder().userStatusEnum(UserStatusEnum.SECESSION).build();

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, ()
                -> userService.signoutUser(signoutRequestDto, user)
        );

        // then
        assertEquals("이미 탈퇴된 사용자입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("User SignOut Password Test - 실패")
    void test4() {
        // given
        signoutRequestDto = SignoutRequestDto.builder().password("password").build();
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when
        PasswordRuntimeException exception = assertThrows(PasswordRuntimeException.class, ()
                -> userService.signoutUser(signoutRequestDto, user)
        );

        // then
        assertEquals("잘못된 비밀번호입니다.", exception.getMessage());
    }
}
