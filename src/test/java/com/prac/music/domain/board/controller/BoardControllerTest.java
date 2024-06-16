package com.prac.music.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.domain.user.entity.User;
import com.prac.music.withMockCustom.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @Mock
    User user;

    BoardRequestDto requestDto;

    @BeforeEach
    public void setup() {
        String title = "Text title";
        String content = "Text content";
        requestDto = new BoardRequestDto(title, content);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Create Test")
    void test1() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(requestDto);
        MockMultipartFile files = new MockMultipartFile("file", "file".getBytes());
        MockMultipartFile board = new MockMultipartFile("board", "", "application/json", content.getBytes(StandardCharsets.UTF_8));

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST,"/api/boards")
                        .file(files)
                        .file(board)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Update Test")
    void test2() throws Exception {
        // given
        Long boardId = 1L;
        String updateTitle = "Update title";
        String updateContent = "Update content";

        BoardRequestDto updateBody = new BoardRequestDto(updateTitle, updateContent);
        String content = objectMapper.writeValueAsString(updateBody);
        MockMultipartFile files = new MockMultipartFile("Update file", "updateFile".getBytes());
        MockMultipartFile board = new MockMultipartFile("board", "", "application/json", content.getBytes(StandardCharsets.UTF_8));

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,"/api/boards/{id}", boardId)
                        .file(files)
                        .file(board)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Delete Test")
    void test3() throws Exception {
        // given
        Long boardId = 1L;

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/boards/{id}", boardId)
                        .with(csrf())
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Get All Test")
    void test4() throws Exception {
        // given
        Board board1 = Board.builder()
                .title("Update title 1")
                .contents("Update content 1")
                .user(user)
                .build();
        Board board2 = Board.builder()
                .title("Update title 2")
                .contents("Update content 2")
                .user(user)
                .build();
        List<BoardResponseDto> boards = Arrays.asList(
                new BoardResponseDto(board1),
                new BoardResponseDto(board2)
        );

        // when
        Mockito.when(boardService.getAllBoard()).thenReturn(boards);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/boards/list")
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Get Test")
    void test5() throws Exception {
        // given
        Long boardId = 1L;

        Board board = Board.builder()
                .title("title 1")
                .contents("content 1")
                .user(user)
                .build();
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        // when
        Mockito.when(boardService.getBoardById(boardId)).thenReturn(boardResponseDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/boards/{id}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockCustomUser
    @DisplayName("Board Pagination Test")
    void test6() throws Exception {
        // given
        int page = 1;
        int size = 10;
        String sort = "updatedAt";
        String direction = "DESC";

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/boards")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .param("direction", direction)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}