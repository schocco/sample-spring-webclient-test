package com.mimacom.springdemos.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoService {

    private final WebClient todoApiClient;

    public TodoService(WebClient.Builder webClientBuilder, TodoConfigurationProperties todoProps) {
        this.todoApiClient = webClientBuilder
                .baseUrl(todoProps.getBaseUrl().toString())
                .defaultRequest(requestHeadersSpec -> requestHeadersSpec.accept(MediaType.APPLICATION_JSON))
                .build();
    }

    public Flux<TodoDto> findTodos() {
        return this.todoApiClient.get().retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new TodoServiceUnavailableException()))
                .bodyToFlux(TodoDto.class);
    }
}
