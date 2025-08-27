package ru.izelinov.draganddrop.configuration.rest;

import org.springframework.http.HttpStatus;

public record ErrorInfo(
    String message,
    HttpStatus status,
    Long timestamp
) {

}
