package com.prac.music.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.comment.dto.CommentResponseDto;
import com.prac.music.domain.comment.dto.CommentUpdateRequestDto;
import com.prac.music.domain.comment.entity.Comment;
import com.prac.music.domain.comment.service.CommentService;
import com.prac.music.domain.user.entity.User;
import com.prac.music.withMockCustom.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Mock
    User user;

    @Mock
    Board board;

    CommentResponseDto responseDto;
    CommentUpdateRequestDto updateRequestDto;

    Comment comment;

    @BeforeEach
    public void setup() {
        comment = Comment.builder()
                .contents("contents")
                .board(board)
                .user(user)
                .build();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Comment Create Test")
    void test1() throws Exception {
        // given
        Long boardId = 1L;
        responseDto = new CommentResponseDto(comment);

        String postJson = objectMapper.writeValueAsString(responseDto);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/boards/{boardId}/comments", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Comment Update Test")
    void test2() throws Exception {
        // given
        Long boardId = 1L;
        Long commentId = 1L;
        String updateContent = "Update contents";

        updateRequestDto = new CommentUpdateRequestDto(updateContent);

        comment = Comment.builder()
                .contents(updateRequestDto.getContents())
                .board(board)
                .user(user)
                .build();

        responseDto = new CommentResponseDto(comment);

        String PostJson = objectMapper.writeValueAsString(responseDto);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,"/api/boards/{boardId}/comments/{commentId}", boardId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PostJson)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Comment Delete Test")
    void test3() throws Exception {
        // given
        Long boardId = 1L;
        Long commentId = 1L;

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/boards/{boardId}/comments/{commentId}", boardId, commentId)
                .with(csrf())
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}