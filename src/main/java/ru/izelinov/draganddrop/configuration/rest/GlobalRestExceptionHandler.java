package ru.izelinov.draganddrop.configuration.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.izelinov.draganddrop.utils.exception.GeneralApplicationException;
import ru.izelinov.draganddrop.utils.exception.InvalidArgumentException;
import ru.izelinov.draganddrop.utils.exception.InvalidOperationException;
import ru.izelinov.draganddrop.utils.exception.NotFoundException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@ControllerAdvice
class GlobalRestExceptionHandler {

  @ExceptionHandler
  protected ResponseEntity<ErrorInfo> handleHttpMessageConversionException(
      InvalidOperationException e
  ) {
    log.error(e.getMessage(), e);

    return new ResponseEntity<>(
        new ErrorInfo(
            isBlank(e.getMessage()) ? "" : e.getMessage(),
            HttpStatus.BAD_REQUEST,
            System.currentTimeMillis()
        ),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorInfo> handleHttpMessageConversionException(
      InvalidArgumentException e
  ) {
    log.error(e.getMessage(), e);

    return new ResponseEntity<>(
        new ErrorInfo(
            isBlank(e.getMessage()) ? "" : e.getMessage(),
            HttpStatus.UNPROCESSABLE_ENTITY,
            System.currentTimeMillis()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    );
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorInfo> handleHttpMessageConversionException(
      NotFoundException e
  ) {
    log.error(e.getMessage(), e);

    return new ResponseEntity<>(
        new ErrorInfo(
            isBlank(e.getMessage()) ? "" : e.getMessage(),
            HttpStatus.NOT_FOUND,
            System.currentTimeMillis()
        ),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorInfo> handleHttpMessageConversionException(
      GeneralApplicationException e
  ) {
    log.error(e.getMessage(), e);

    return new ResponseEntity<>(
        new ErrorInfo(
            isBlank(e.getMessage()) ? "" : e.getMessage(),
            HttpStatus.BAD_REQUEST,
            System.currentTimeMillis()
        ),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorInfo> handleHttpMessageConversionException(
      Throwable t
  ) {
    log.error(t.getMessage(), t);

    return new ResponseEntity<>(
        new ErrorInfo(
            "",
            HttpStatus.INTERNAL_SERVER_ERROR,
            System.currentTimeMillis()
        ),
        HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
