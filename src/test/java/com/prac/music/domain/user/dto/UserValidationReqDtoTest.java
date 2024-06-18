package com.prac.music.domain.user.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserValidationReqDtoTest {

    SignupRequestDto signupReqDto;
    ProfileRequestDto profileReqDto;
    Validator validator;

    @BeforeEach
    void setUp() {
        signupReqDto = SignupRequestDto.builder()
                .userId("dds12")
                .password("asdf12")
                .name("asd1f")
                .email("email")
                .intro(" ")
                .build();

        profileReqDto = ProfileRequestDto.builder()
                .name("asdf1")
                .email("email")
                .intro("")
                .password("asdfd123")
                .newPassword("tes12e")
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Locale.setDefault(Locale.KOREAN);
    }

    @Test
    @DisplayName("Signup Validation Test")
    void test1() {
        // given

        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupReqDto);
        List<String> messageList = new ArrayList<>();

        for (ConstraintViolation<SignupRequestDto> violation : violations) {
            String message = violation.getMessage();
            messageList.add(message);
            System.out.println("message = " + message);
        }

        // then

        assertThat(messageList).containsOnly(
                "유효한 이름을 입력해주세요.",
                "유효한 닉네임을 입력해주세요.",
                "소개글 입력은 필수입니다.",
                "비밀번호는 최소 10자 이상 입니다.",
                "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.",
                "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
        );

    }

    @Test
    @DisplayName("Profile Validation Test")
    void test2() {
        // given

        // when
        Set<ConstraintViolation<ProfileRequestDto>> violations = validator.validate(profileReqDto);
        List<String> messageList = new ArrayList<>();

        for (ConstraintViolation<ProfileRequestDto> violation : violations) {
            String message = violation.getMessage();
            messageList.add(message);
            System.out.println("message = " + message);
        }

        // then
        assertThat(messageList).containsOnly(
                "소개글 입력은 필수입니다.",
                "유효한 이메일 주소를 입력해주세요.",
                "유효한 이름을 입력해주세요.",
                "비밀번호는 최소 10자 이상 입니다.",
                "비밀번호는 최소 10자 이상 입니다.",
                "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.",
                "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
        );
    }
}