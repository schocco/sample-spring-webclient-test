package com.mimacom.springdemos.demo;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class TodoServiceTest {


    private MockWebServer mockWebServer;
    private TodoConfigurationProperties todoProps;
    private TodoService apiCaller;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        todoProps = new TodoConfigurationProperties();
        todoProps.setBaseUrl(mockWebServer.url("/").url());
        apiCaller = new TodoService(WebClient.builder(), todoProps);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

    @Test
    void shouldReturnTodoDtos() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("[]")
        );
        assertThat(this.apiCaller.findTodos().collectList().block()).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldIgnoreAdditionalProperties() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("[{\"title\":\"test\", \"id\":5, \"extra\":\"extra\"}]")
        );
        List<TodoDto> todoDtos = this.apiCaller.findTodos().collectList().block();
        assertThat(todoDtos).hasSize(1);
        assertThat(todoDtos.get(0).getTitle()).isEqualTo("test");
        assertThat(todoDtos.get(0).getId()).isEqualTo(5);
    }

    @Test
    void shouldMap5XXErrorResponseToCustomException() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .setBody("HTTP 500")
        );
        assertThatThrownBy(() -> this.apiCaller.findTodos().collectList().block())
                .isInstanceOf(TodoServiceUnavailableException.class);
    }
}
