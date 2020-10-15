package com.mimacom.springdemos.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private TodoService todoService;


    @Test
    void shouldHandleServerUnavailability() {
        when(todoService.findTodos()).thenReturn(Flux.error(new TodoServiceUnavailableException()));
        this.webClient.get().exchange().expectStatus().isEqualTo(HttpStatus.BAD_GATEWAY);
    }

    @Test
    void shouldReturnTodos() {
        // given
        TodoDto firstTodo = new TodoDto(1, 1, "Write unit tests", true);
        TodoDto secondTodo = new TodoDto(1, 2, "Monitor test coverage", false);
        TodoDto[] responsePayload = new TodoDto[]{firstTodo, secondTodo};
        when(todoService.findTodos()).thenReturn(Flux.fromArray(responsePayload));
        // when
        WebTestClient.ResponseSpec responseSpec = this.webClient.get().exchange();
        // then
        responseSpec.expectStatus().is2xxSuccessful();
        responseSpec.expectBodyList(TodoDto.class).contains(firstTodo, secondTodo);
    }
}
