package com.prac.music.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class SignoutRequestDto {
    private String password;
}
