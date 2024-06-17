package com.prac.music.domain.user.security;

import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.entity.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    User user;
    UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId("userId")
                .password("password")
                .userStatusEnum(UserStatusEnum.NORMAL)
                .build();

        userDetails = new UserDetailsImpl(user);
    }

    @Test
    @DisplayName("UserDetailsImpl getUser Test")
    void test1() {
        assertEquals(user, userDetails.getUser());
    }

    @Test
    @DisplayName("getAuthorities Test")
    void test2() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(UserStatusEnum.NORMAL.getAuthority())));
    }

    @Test
    @DisplayName("getPassword Test")
    void test3() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    @DisplayName("getUsername Test")
    void test4() {
        assertEquals("userId", userDetails.getUser().getUserId());
    }

    @Test
    @DisplayName("isAccountNonExpired Test")
    void test5() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    @DisplayName("isAccountNonLocked Test")
    void test6() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    @DisplayName("isCredentialsNonExpired Test")
    void test7() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("isEnabled Test")
    void test8() {
        assertTrue(userDetails.isEnabled());
    }

}