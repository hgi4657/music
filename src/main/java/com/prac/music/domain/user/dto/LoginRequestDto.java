package com.prac.music.domain.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LoginRequestDto {
    private String userId;

    private String password;
}