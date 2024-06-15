package com.prac.music.withMockCustom;

import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.entity.UserStatusEnum;
import com.prac.music.domain.user.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
        WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                User.builder()
                .userId("test1")
                .password("1234")
                .userStatusEnum(UserStatusEnum.NORMAL)
                .build()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities()
        );

        context.setAuthentication(authentication);
        return context;
    }
}