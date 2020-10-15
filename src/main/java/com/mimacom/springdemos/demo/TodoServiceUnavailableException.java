package com.mimacom.springdemos.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class TodoServiceUnavailableException extends RuntimeException {
}
