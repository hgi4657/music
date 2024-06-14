package com.prac.music.domain.board.entity;

import com.prac.music.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
    Board board;

    @Mock
    User user;

    @BeforeEach
    void setUp() {
        board = Board.builder()
                .title("Test Title")
                .contents("Test Contents")
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Board Constructor Test")
    void test1() {
        // given
        String title = "Test Title";
        String contents = "Test Contents";

        // when
        Board board = Board.builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();

        // then
        assertEquals(title, board.getTitle());
        assertEquals(contents, board.getContents());
        assertEquals(user, board.getUser());
    }

    @Test
    @DisplayName("Board Update Test")
    void test2() {
        // given
        String title = "Update Title";
        String contents = "Update Contents";

        // when
        board.update(title, contents);

        // then
        assertEquals(title, board.getTitle());
        assertEquals(contents, board.getContents());
    }
}