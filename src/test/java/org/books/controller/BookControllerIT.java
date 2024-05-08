package org.books.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.books.config.security.SecurityConfiguration;
import org.books.controller.dto.BookDto;
import org.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.books.controller.BookController.BOOKS_ENDPOINT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("security")
@Import({SecurityConfiguration.class})
public class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void delete403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BOOKS_ENDPOINT + "/isbn1")
                .with(csrf())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void delete400() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(BOOKS_ENDPOINT + "/   ").with(csrf())).andExpect(status().isBadRequest()).andReturn();
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void delete200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BOOKS_ENDPOINT + "/isbn1")
                .with(csrf())).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void post200() throws Exception {
        final String isbn="isbn123";
        final String title="Book title";
        final BigDecimal price= BigDecimal.valueOf(12.99);
        final Long genreId=1L;
        final List<Long> authorIds = Stream.of(2L, 1L).toList();
        final BookDto bookDto = new BookDto(isbn, title, price, genreId, authorIds);
        String json = objectMapper.writeValueAsString(bookDto);
        when(bookService.save(bookDto)).thenReturn(bookDto);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BOOKS_ENDPOINT ).contentType(MediaType.APPLICATION_JSON)
                        .content(json).with(csrf())).andExpect(status().isOk()).andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        final BookDto response =objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(bookDto.getIsbn(), response.getIsbn());
        assertEquals(bookDto.getTitle(), response.getTitle());
        assertEquals(bookDto.getPrice(), response.getPrice());
        assertEquals(bookDto.getGenreId(), response.getGenreId());
        assertEquals(2,response.getAuthorIds().size());
        assertTrue(response.getAuthorIds().containsAll(Stream.of(2L, 1L).toList()));
    }
}
