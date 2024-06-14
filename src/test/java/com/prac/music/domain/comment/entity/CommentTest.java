package com.prac.music.domain.comment.entity;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {
    Comment comment;

    @Mock
    Board board;

    @Mock
    User user;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .contents("Text contents")
                .board(board)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Comment Constructor Test")
    void test() {
        // given
        String content = "Text contents";

        // when
        Comment comment = Comment.builder()
                .contents(content)
                .board(board)
                .user(user)
                .build();

        // then
        assertEquals(content, comment.getContents());
    }

    @Test
    @DisplayName("Comment update Test")
    void test1() {
        // given
        String content = "Update contents";

        // when
        comment.update(content);

        // then
        assertEquals(content, comment.getContents());
    }
}